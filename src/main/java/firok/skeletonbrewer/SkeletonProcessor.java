package firok.skeletonbrewer;

import firok.topaz.RegexPipeline;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

@SupportedAnnotationTypes({"firok.skeletonbrewer.Skeleton"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SkeletonProcessor extends AbstractProcessor
{
	public SkeletonProcessor() { }

	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Messager messager;
	public void printNote(Object obj)
	{
		messager.printMessage(Diagnostic.Kind.NOTE,"[SkeletonBrewer] " + obj);
	}
	public void printWarning(Object obj)
	{
		messager.printMessage(Diagnostic.Kind.WARNING,"[SkeletonBrewer] " + obj);
	}
	public void printError(Object obj)
	{
		messager.printMessage(Diagnostic.Kind.ERROR,"[SkeletonBrewer] " + obj);
	}

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();

		messager.printMessage(Diagnostic.Kind.NOTE,"SkeletonBrewer ADT 初始化完成");
	}

	private static final Object LOCK_JFO_API = new Object();
	private static final Object LOCK_REFLECT_API = new Object();

	private Writer createSourceFileWrite(String location) throws IOException
	{
		synchronized (LOCK_JFO_API)
		{
			var jfo = filer.createSourceFile(location);
			return jfo.openWriter();
		}
	}

	private static final RegexPipeline rp = new RegexPipeline();
	private String generate(String template, Map<String, String> context)
	{
		return rp.replaceAll(template, context);
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
		var setAnno = roundEnv.getElementsAnnotatedWith(Skeleton.class);
		printNote("本轮找到 %d 个 Skeleton 注解".formatted(setAnno.size()));
		if(setAnno.isEmpty()) return true;

		setAnno.stream().parallel().forEach(ele -> {
			if(ele instanceof TypeElement eleBean)
			{
				Skeleton anno;
				synchronized (LOCK_REFLECT_API) { anno = eleBean.getAnnotation(Skeleton.class); }

				final var skeletonFullName = eleBean.getQualifiedName().toString(); // a.b.c.d.E
				final var skeletonName = eleBean.getSimpleName().toString(); // E
				final var skeletonPackage = skeletonFullName.substring(0, skeletonFullName.lastIndexOf('.')); // a.b.c.d;

				final var keeperFullNameRaw = anno.at();
				final var keeperFullName = switch (keeperFullNameRaw) {
					case Constants.DOLLAR_PREFIX -> skeletonPackage + ".$" + skeletonName;
					case Constants.KEEPER_SUFFIX -> skeletonPackage + "." + skeletonName + "Keeper";
					default -> keeperFullNameRaw;
				};
				final var keeperPackage = keeperFullName.substring(0, keeperFullName.lastIndexOf('.'));
				if(keeperPackage.isEmpty())
				{
					var msg = "包空间不可为空: " + skeletonFullName;
					printError(msg);
					throw new IllegalArgumentException(msg);
				}
				final var keeperName = keeperFullName.substring(keeperFullName.lastIndexOf('.') + 1);

				final var viaName = anno.via();
				final var pattern = anno.in();

				try(var writer = createSourceFileWrite(keeperFullName))
				{
					var template = pattern.template;

					var map = new HashMap<String, String>() {
						@Override
						public String put(String key, String value)
						{
							return super.put(key, Matcher.quoteReplacement(value));
						}
					};
					map.put("##SKELETON_FULL_NAME##", skeletonFullName);
					map.put("##SKELETON_NAME##", skeletonName);
					map.put("##SKELETON_PACKAGE##", skeletonPackage);
					map.put("##KEEPER_FULL_NAME##", keeperFullName);
					map.put("##KEEPER_NAME##", keeperName);
					map.put("##KEEPER_PACKAGE##", keeperPackage);
					map.put("##VIA_NAME##", viaName);

					var content = generate(template, map);

					writer.write(content);
				}
				catch (Exception any)
				{
					printError(any);
				}
			}
		});

		return true;
	}
}

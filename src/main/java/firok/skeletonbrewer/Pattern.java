package firok.skeletonbrewer;

import firok.skeletonbrewer.internal.Templates;

public enum Pattern
{
	EagerLoading(Templates.EagerLoading),
	LazyLoading(Templates.LazyLoading),
	Synchronized(Templates.Synchronized),
	DoubleCheckLocking(Templates.DoubleCheckLocking),
	BillPugh(Templates.BillPugh),
	;

	public final String template;
	Pattern(String template)
	{
		this.template = template;
	}
}

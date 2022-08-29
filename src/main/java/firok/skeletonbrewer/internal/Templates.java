package firok.skeletonbrewer.internal;

public class Templates
{
	public static final String EagerLoading = """
			package ##KEEPER_PACKAGE##;
			
			import ##SKELETON_FULL_NAME##;
			
			public class ##KEEPER_NAME##
			{
			    private static final ##SKELETON_NAME## _instance = new ##SKELETON_NAME##();
			    public static ##SKELETON_NAME## ##VIA_NAME##()
			    {
			        return _instance;
			    }
			}""";
	public static final String LazyLoading = """
			package ##KEEPER_PACKAGE##;
            
			import ##SKELETON_FULL_NAME##;
			
			public class ##KEEPER_NAME##
			{
			    private static ##SKELETON_NAME## _instance = null;
			    public static ##SKELETON_NAME## ##VIA_NAME##()
			    {
			        if(_instance == null)
			        {
			            _instance = new ##SKELETON_NAME##();
			        }
			        return _instance;
			    }
			}""";
	public static final String Synchronized = """
			package ##KEEPER_PACKAGE##;
            
			import ##SKELETON_FULL_NAME##;
			
			public class ##KEEPER_NAME##
			{
			    private static ##SKELETON_NAME## _instance;
			    public static synchronized ##SKELETON_NAME## ##VIA_NAME##()
			    {
			        if(_instance == null)
			            _instance = new ##SKELETON_NAME##();
			        return _instance;
			    }
			}""";
	public static final String DoubleCheckLocking = """
			package ##KEEPER_PACKAGE##;
            
			import ##SKELETON_FULL_NAME##;
			
			public class ##KEEPER_NAME##
			{
			    private volatile static ##SKELETON_NAME## _instance;
			    public static ##SKELETON_NAME## ##VIA_NAME##()
			    {
			        if(_instance == null)
			        {
			            synchronized (##SKELETON_NAME##.class)
			            {
			                if(_instance == null)
			                    _instance = new ##SKELETON_NAME##();
			            }
			        }
			        return _instance;
			    }
			}""";
	public static final String BillPugh = """
			package ##KEEPER_PACKAGE##;
            
			import ##SKELETON_FULL_NAME##;
			
			public class ##KEEPER_NAME##
			{
			    private static class $##KEEPER_NAME##
			    {
			        private static final ##SKELETON_NAME## instance = new ##SKELETON_NAME##();
			    }

			    public static ##SKELETON_NAME## ##VIA_NAME##()
			    {
			        return $##KEEPER_NAME##.instance;
			    }
			}""";
}

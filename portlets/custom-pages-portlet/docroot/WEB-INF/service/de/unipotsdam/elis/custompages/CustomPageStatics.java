package de.unipotsdam.elis.custompages;

public class CustomPageStatics {
	
	//public final static List<String> CUSTOM_PAGE_TEMPLATE_NAMES = Arrays.asList(new String[] { "CustomPage" });
	
	public final static String PAGE_TYPE_CUSTOM_FIELD_NAME = "CustomPageType";
	
	public final static int FEEDBACK_UNREQUESTED = 0;
	public final static int FEEDBACK_REQUESTED = 1;
	public final static int FEEDBACK_DELIVERED = 2;
	
	public final static int PUBLISHMENT_TYPE_GLOBAL = 0;
	
	public final static int CUSTOM_PAGE_TYPE_NONE = 0;
	public final static int CUSTOM_PAGE_TYPE_NORMAL_PAGE = 1;
	public final static int CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE = 2;
	
	public final static String WIKI_LAYOUT_PROTOTYPE = "Seite mit einem Wiki";
	public final static String BLOG_LAYOUT_PROTOTYPE = "Seite mit einem Blog";
	public final static String CDP_LAYOUT_PROTOTYPE = "Seite mit beliebigem Inhalt";
	public final static String EMPTY_LAYOUT_PROTOTYPE = "Frei gestaltbare Seite";
	
	public final static int MESSAGE_TYPE_CUSTOM_PAGE_CREATED = 0;
	public final static int MESSAGE_TYPE_CUSTOM_PAGE_PUBLISHED = 1;
	public final static int MESSAGE_TYPE_FEEDBACK_REQUESTED = 2;
	public final static int MESSAGE_TYPE_FEEDBACK_DELIVERED = 3;
}

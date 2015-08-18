package de.unipotsdam.elis.portfolio;

import java.util.Arrays;
import java.util.List;

public class PortfolioStatics {
	
	public final static List<String> PORTFOLIO_TEMPLATE_NAMES = Arrays.asList(new String[] { "Portfolio" });
	
	public static int FEEDBACK_UNREQUESTED = 0;
	public static int FEEDBACK_REQUESTED = 1;
	public static int FEEDBACK_DELIVERED = 2;
	
	public static int PUBLISHMENT_INDIVIDUAL = 0;
	public static int PUBLISHMENT_GLOBAL = 1;
	
	public final static String WIKI_LAYOUT_PROTOTYPE = "Wiki";
	public final static String BLOG_LAYOUT_PROTOTYPE = "Blog";
	public final static String CDP_LAYOUT_PROTOTYPE = "Content Display Page";
	public final static String EMPTY_LAYOUT_PROTOTYPE = "Empty";
	
	public final static int MESSAGE_TYPE_PORTFOLIO_CREATED = 0;
	public final static int MESSAGE_TYPE_PORTFOLIO_PUBLISHED = 1;
	public final static int MESSAGE_TYPE_FEEDBACK_REQUESTED = 2;
	public final static int MESSAGE_TYPE_FEEDBACK_DELIVERED = 3;
}

package itmclient;

/**
 * <Description>
 *
 * @author DaveySE
 *         Creation Date: 4/28/14
 *         Copyright © 2013 Intellectual Reserve, Inc. All Rights reserved.
 */
public class EnquireTerms {

  public static final String LOCAL_TERM_CLASS = "http://lds.org/terminology/model#LocalTerm"; // "100019";
  public static final String LOCAL_TERM_ASSOC = "http://lds.org/terminology/model#LocalTerms"; // "100031";  (Data Item Type - Pointer to a Term)
  public static final String TERM_CLASS = "http://lds.org/terminology/model#Term"; // "100017";
  public static final String IN_GROUP_ASSOC = "http://www.mondeca.com/system/t3#inGroup"; // "100062"  (Data Item Type - Pointer)
  public static final String LOCALE_ASSOC = "http://lds.org/terminology/model#locale"; // "100072";  (Data Item Type - Pointer to a Language)
  public static final String BCP_BASE_URI = "http://lds.org/terminology/locale/";

  /*
   * Languages:
   *  en - 103450 - http://lds.org/terminology/locale/en ; 29 - http://www.lingvoj.org/lang/en
   *  fr - 103449 - http://lds.org/terminology/locale/fr  ; 80 - http://www.lingvoj.org/lang/fr
   */

  /**
   * Generate XML to retrieve all Local Terms for the specified locales for all Terms in the specified List
   * @param listId Id of the List to be itemized
   * @param locales Comma separated list of BCP-47 locales to be retrieved
   * @return XML string for the enquiry
   */
  public String buildLocalTermEnquiry(String listId, String locales) {
    StringBuilder enquiry = new StringBuilder();
    enquiry.append("<query>").append("\n")
      .append("<topic-filter classes=\"").append(LOCAL_TERM_CLASS).append("\">").append("\n")
        .append("<role-filter>").append("\n")
          .append("<association-filter classes=\"").append(LOCAL_TERM_ASSOC).append("\">").append("\n")
            .append("<role-filter>").append("\n")
              .append("<topic-filter classes=\"").append(TERM_CLASS).append("\">").append("\n")
                .append("<attribute-filter>").append("\n")
                  .append("<pointer-filter types=\"").append(IN_GROUP_ASSOC).append("\" topicIds=\"").append(listId).append("\" />").append("\n")
                .append("</attribute-filter>").append("\n")
              .append("</topic-filter>").append("\n")
            .append("</role-filter>").append("\n")
          .append("</association-filter>").append("\n")
        .append("</role-filter>").append("\n")
        .append("<attribute-filter>").append("\n")
          .append("<pointer-filter types=\"").append(LOCALE_ASSOC).append("\" topicIds=\"").append(breakOutLocaleTokens(locales)).append("\" />").append("\n")
        .append("</attribute-filter>").append("\n")
      .append("</topic-filter>").append("\n")    // Local Term Class
      .append("<topic-properties>").append("\n")
      .append("<name-filter types=\"1\"/>").append("\n")
      .append("</topic-properties>").append("\n")
      .append("</query>");
    return enquiry.toString();
  }

  /**
   * Generate XML to retrieve all Terms in the specified List
   * @param listId Id of the List to be itemized
   * @param locales Comma separated list of BCP-47 locales to be retrieved
   * @return XML string for the enquiry
   */
  public String buildTermEnquiry(String listId) {
    StringBuilder enquiry = new StringBuilder();
    enquiry.append("<query includeReferentialTopics=\"true\">").append("\n")
      .append("<topic-filter classes=\"").append(TERM_CLASS).append("\">").append("\n")
        .append("<attribute-filter>").append("\n")
          .append("<pointer-filter types=\"").append(IN_GROUP_ASSOC).append("\" topicIds=\"").append(listId).append("\" />").append("\n")
        .append("</attribute-filter>").append("\n")
      .append("</topic-filter>").append("\n")
      .append("\n")
      .append("<topic-properties>").append("\n")
      .append("<name-filter/>").append("\n")
      .append("<pointer-filter/>").append("\n")
      .append("</topic-properties>").append("\n")
      .append("</query>");
    return enquiry.toString();
  }



  private String breakOutLocaleTokens(String locales) {
    String[] tokens = locales.split(",");
    StringBuilder s = new StringBuilder();
//    for(String t: tokens) {
//      s.append(BCP_BASE_URI).append(t).append(",");
//    }
//    s.setLength(s.length()-1); // remove last comma
    s.append("29");
    return s.toString();
  }
}

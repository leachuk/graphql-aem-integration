<%@page session="false"%>
<%@include file="/libs/foundation/global.jsp" %>

<h1>Kablamo Home</h1>

<div>
    <cq:include resourceType="capi-aem-poc-project/components/content/kablamo/bigheader" path="big_header"/>
    <cq:include resourceType="capi-aem-poc-project/components/content/kablamo/aboutus" path="about_us_section"/>
    <cq:include resourceType="capi-aem-poc-project/components/content/kablamo/contactdetails" path="contact_details"/>
    <cq:include resourceType="capi-aem-poc-project/components/content/kablamo/officelocations" path="office_locations"/>
    <cq:include resourceType="capi-aem-poc-project/components/content/kablamo/bigquote" path="big_quote"/>
</div>

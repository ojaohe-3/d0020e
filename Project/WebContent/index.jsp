
<%--
  Created by IntelliJ IDEA.
  User: JohanLap
  Date: 2020-01-27
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>

      <title>Study Planner</title>
      
  </head>
  <body>

  <%@include file="_includes.jsp" %>

<!-- Pop up to accept cookies and store accepted answer for 30 days -->
<link rel="stylesheet" href="CssFolder/jquery.ihavecookies.css">
<script type="text/javascript" src="JavascriptFolder/jquery.ihavecookies.js"></script>
<script type="text/javascript">
    var options = {
        title: 'Accept Cookies? &#x1F36A;',
        message: 'The site uses cookies to ensure you get the best experience.',
        delay: 600,
        expires: 30,
        onAccept: function(){
            var myPreferences = $.fn.ihavecookies.cookie();
            console.log('Yay! The following preferences were saved...');
            console.log(myPreferences);
        },
        uncheckBoxes: true,
        acceptBtnLabel: 'Accept Cookies',
    }

    $(document).ready(function() {
        $('body').ihavecookies(options);

        $('#ihavecookiesBtn').on('click', function(){
            $('body').ihavecookies(options, 'reinit');
        });
    });
</script>

   <%@include  file="_filterContainer.jsp" %>

   <%@include file="_graphCanvas.jsp"%>
   <div class="container" style="background-color:yellow;" id="debug"></div>
  </body>
</html>

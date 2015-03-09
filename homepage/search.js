$(function() {
  $("#divTest1").text("Hello, world!");   
});

function ShowBox()
{
        $("#testFade").slideToggle("slow");
}

function DisplayOnHTML(json) {
  console.log("In DisplayOnHTML " + JSON.stringify(json));  
  document.body.style.backgroundColor = "#CCFFCC";
  
  var searchList = document.getElementById("listOfSearches");
  for (var i = 0; i < json.results.length; i++) {
    var newSearchListItem = document.createElement("li");
    var searchListValue = document.createTextNode("Title " + i + ": " + json.results[i].title);
    newSearchListItem.appendChild(searchListValue);
    searchList.appendChild(newSearchListItem);
  }
}

//$(document).ready(function() {
  $("#Button").click(function(event){
    var query = document.getElementById("inputBar");
    queryValue = query.value;
    console.log("QUERY VALUE: " + queryValue);

    $.getJSON("http://localhost:8080/Testingpart2/TestingServlet2", function(json, textStatus) {
      //var json = JSON.parse(data);
      console.log("Here is the response: " + JSON.stringify(json)); 
      console.log("query: " + json.query);
      console.log("results: " + json.results[0]);
      console.log("title" + json.results[0].title);
      console.log("body" + json.results[1].body);
      DisplayOnHTML(json);
    });
  });
//});
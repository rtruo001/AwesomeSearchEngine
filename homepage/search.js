$(document).ready(function(){$(".entireWebPage").hide().fadeIn(1000)});

$(function() {
  $("#divTest1").text("Hello, world!");   
});

function ShowBox() {
  var searchList = document.getElementById("listOfSearches");

  $('#SearchChoices').fadeOut("slow");
  for (var i = searchList.childNodes.length-1; i >= 0; i--) {
    searchList.removeChild(searchList.childNodes[i]);
  }
}

function DisplayOnHTML(json) {
  console.log("In DisplayOnHTML " + JSON.stringify(json));  
  
  var searchChoices = document.getElementById("SearchChoices");
  var searchList = document.getElementById("listOfSearches");
  for (var i = 0; i < json.results.length; i++) {
    var newSearchListItem = document.createElement("li");
    var searchListValue = document.createTextNode(i+1 + " - " + json.results[i].title);
    var newLine = document.createElement("br");
    newSearchListItem.appendChild(searchListValue);
    newSearchListItem.appendChild(newLine);
    searchList.appendChild(newSearchListItem);
  }
  $('#SearchChoices').fadeIn({queue: false, duration: 'slow'});
  $('#SearchChoices').animate({ top: "0" }, '1000');
  searchChoices.style.display='block';

}

//$(document).ready(function() {
$("#Button").click(function(event){
  var query = document.getElementById("inputBar");
  queryValue = query.value;
  console.log("QUERY VALUE: " + queryValue);

  $.getJSON("http://localhost:8080/AwesomeSearchEngine/ASEQuery?query=" + queryValue, function(json, textStatus) {
    console.log("Here is the response: " + JSON.stringify(json)); 
    console.log("query: " + json.query);
    console.log("results: " + json.results[0]);
    console.log("title" + json.results[0].title);
    console.log("body" + json.results[1].body);
    DisplayOnHTML(json);
  });
});
//});
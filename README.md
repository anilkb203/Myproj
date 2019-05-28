# Myproj
pre-condition:
---------------
1.Install the required software like jdk8,android sdk 145 bundle,eclipse IDE ,selenium webdriver jar(3.14.0),selenium standalone jar,appium java client jar(1.6.0) and testng enabled.

2.Need to install Appium desktop server and start appium server manually 

2.In android mobile settings, "USB debugging" and "stayawake" options should be selected




-------------Test cases-------------------

1.check the Ebay app login functionality----not checked --- unable to detect the homescreen using appium desktop sever.

2.Search the item in the search box(ex.-65-inch tv)---done

3.Display the search results.----done

4.select the item from the search result randomly(condition : ignore 1st and last item in the searchlist_displayed)---partially done

5.selected random details should be displayed.(ex- name, price & description);--partially done

6.Compare the search results with the product details.---- need to be checked

7.get the orientation of the screens(portrait ,Landscape)---partially done 

8.validate all the required details with Assertions class--- partially done

9.read the data from external source(ex- text,excel, properties file...etc)---partially done

10.handle diff screen resolutions---need to be checked
.

note:
-----

check the +ve and -ve testcases for all the test scenarios.--- not done 

observations:
---------------

1.In the display_results, apart from TV , other product is getting displayed though filtering is applied.

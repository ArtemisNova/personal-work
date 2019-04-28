The data dump used in this project is available here https://rainbow6.ubisoft.com/siege/en-us/news/152-293696-16/introduction-to-the-data-peek-velvet-shell-statistics
Its the largest file

To take a segment from the data, use the cpp script in scripts
then from there, sort the new csv file created with either excel or calc, sorting by matchid and then round num
Use this file in StripEmptyRounds.java to remove rounds that will not work in the scripts, ie incomplete rounds 
This will create stripped_file.csv that will be used for the other scripts.
WinFrequency.java will create win_rates.txt for GrabFeatures.java to use

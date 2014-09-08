REM In case the services need to be re-created, this is how to remove them (after stopping them in Services)

sc.exe delete SELENIUMHUB
sc.exe delete SELENIUMCHROME
sc.exe delete SELENIUMIE9
sc.exe delete SELENIUMSAFARI
sc.exe delete SELENIUMFIREFOX

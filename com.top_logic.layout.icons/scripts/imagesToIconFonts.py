import os

theme_settings = "theme-settings.xml"
with open(theme_settings, "r") as themeSettings:
	readThemeSettings = themeSettings.read()
	path = os.path.dirname(os.path.realpath(__file__))
	svgCssMappingFile = path + '\imageIconFontMapping.txt' # must be in the same directory as this python file
	with open(svgCssMappingFile) as file:
		for line in file:
			s = line.split("=")
			s[1] = s[1].replace("\r", "").replace("\n", "")
			patternList = s[0].split()
			for pattern in patternList:
				readThemeSettings = readThemeSettings.replace(pattern, s[1])
	themeSettings.close()
themeSettingsW = open(theme_settings, "w")
themeSettingsW.write(readThemeSettings)
themeSettingsW.close()
# spriteIcons
Generates a css/png icon sprite from 32x32 png icon files. A common source for these types of icons is defaulticon.com

## To generate icons
* Make sure your icons are 32x32 pixels
* Your icons' filenames will be used to generate css class names, so name them accordingly (ie. "search.png", "menu.png")
* Place your icons in the icons_input directory
* Run SpriteIcons.java

This will generate a png sprite, corresponding css, and a sample html file (for reference and testing).

## Adding icons to html: 
A typical use would be: 
```html
<span class="icon icon-pin icon-blue"></span>
```
This will display a blue pin icon. 

If you want an icon to change color on hover, add the class "icon-hover". eg
```html
<span class="icon icon-pin icon-white icon-hover"></span>
```
## Customization: 
You can customize the colors and corresponding class names by update the values of arrays 'colors' and 'colorNames' in SpriteIcons.java

RGB color values are used (eg. sky blue is:  [20,108,173] )

# spriteIcons
Generates a css/png icon sprite from 32x32 icon files.

## Requirements
Original icons must be 32x32 png files stored in the icons_input directory. A good source for for 32px icons is defaulticon.com

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
You can customize the colors and corresponding class names by update the values of the colors and colorNames arrays in SpriteIcons.java
RGB color values are used (eg. sky blue is:  [20,108,173] )

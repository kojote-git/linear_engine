## Linear
**Linear** - it's a simple game engine made for learning
concepts of creating game engines.
It's based on [LWJGL 3](https://www.lwjgl.org/) which provides
low-level capabilities for graphics (OpenGL), audio (OpenAL) etc.

### Platform switching

To change platform go to **pom.xml** and edit tag
```xml
<lwjgl.natives>platform</lwjgl.natives>
```
Where instead of **platform** put the string that corresponds to your platform:
* Linux - ``natives-linux``
* Windows - ``natives-windows``
* MacOS - ``natives-macos``


# MYGZ

## 介绍

这是一款静态服务器，使用yaml作为配置文件

```yaml
sites:
  -
    addr: localhost
    port: 8089
    dir: E:\test server\static
    translates:
      -
        origin: .php # 欺骗性的后缀名
        current: .html
    redirects:
      -
        include: "[\S]*\.html"# 此功能用于单页
        exclude: ["index\.html"]
        to: "index.html"

```

## 注意

现在支持的功能较少，不支持php等动态语言

使用idea+gradle开发，但是编译时应该选择idea

位置File | Settings | Build, Execution, Deployment | Build Tools | Gradle
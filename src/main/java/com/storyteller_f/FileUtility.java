package com.storyteller_f;

import java.util.HashMap;

public class FileUtility {
    private static FileUtility fileUtility;
    private final HashMap<String, String> fileTypeMap;

    private FileUtility() {
        fileTypeMap = new HashMap<>();
        //region hashmap
        fileTypeMap.put("", "application/octet-stream");
        fileTypeMap.put("323", "text/h323");
        fileTypeMap.put("acx", "application/internet-property-stream");
        fileTypeMap.put("ai", "application/postscript");
        fileTypeMap.put("aif", "audio/x-aiff");
        fileTypeMap.put("aifc", "audio/x-aiff");
        fileTypeMap.put("aiff", "audio/x-aiff");
        fileTypeMap.put("asf", "video/x-ms-asf");
        fileTypeMap.put("asr", "video/x-ms-asf");
        fileTypeMap.put("asx", "video/x-ms-asf");
        fileTypeMap.put("au", "audio/basic");
        fileTypeMap.put("avi", "video/x-msvideo");
        fileTypeMap.put("axs", "application/olescript");
        fileTypeMap.put("bas", "text/plain");
        fileTypeMap.put("bcpio", "application/x-bcpio");
        fileTypeMap.put("bin", "application/octet-stream");
        fileTypeMap.put("bmp", "image/bmp");
        fileTypeMap.put("c", "text/plain");
        fileTypeMap.put("cat", "application/vnd.ms-pkiseccat");
        fileTypeMap.put("cdf", "application/x-cdf");
        fileTypeMap.put("cer", "application/x-x509-ca-cert");
        fileTypeMap.put("class", "application/octet-stream");
        fileTypeMap.put("clp", "application/x-msclip");
        fileTypeMap.put("cmx", "image/x-cmx");
        fileTypeMap.put("cod", "image/cis-cod");
        fileTypeMap.put("cpio", "application/x-cpio");
        fileTypeMap.put("crd", "application/x-mscardfile");
        fileTypeMap.put("crl", "application/pkix-crl");
        fileTypeMap.put("crt", "application/x-x509-ca-cert");
        fileTypeMap.put("csh", "application/x-csh");
        fileTypeMap.put("css", "text/css");
        fileTypeMap.put("dcr", "application/x-director");
        fileTypeMap.put("der", "application/x-x509-ca-cert");
        fileTypeMap.put("dir", "application/x-director");
        fileTypeMap.put("dll", "application/x-msdownload");
        fileTypeMap.put("dms", "application/octet-stream");
        fileTypeMap.put("doc", "application/msword");
        fileTypeMap.put("dot", "application/msword");
        fileTypeMap.put("dvi", "application/x-dvi");
        fileTypeMap.put("dxr", "application/x-director");
        fileTypeMap.put("eps", "application/postscript");
        fileTypeMap.put("etx", "text/x-setext");
        fileTypeMap.put("evy", "application/envoy");
        fileTypeMap.put("exe", "application/octet-stream");
        fileTypeMap.put("fif", "application/fractals");
        fileTypeMap.put("flr", "x-world/x-vrml");
        fileTypeMap.put("gif", "image/gif");
        fileTypeMap.put("gtar", "application/x-gtar");
        fileTypeMap.put("gz", "application/x-gzip");
        fileTypeMap.put("h", "text/plain");
        fileTypeMap.put("hdf", "applicatin/x-hdf");
        fileTypeMap.put("hlp", "application/winhlp");
        fileTypeMap.put("hqx", "application/mac-binhex40");
        fileTypeMap.put("hta", "application/hta");
        fileTypeMap.put("htc", "text/x-component");
        fileTypeMap.put("htm", "text/html");
        fileTypeMap.put("html", "text/html");
        fileTypeMap.put("htt", "text/webviewhtml");
        fileTypeMap.put("ico", "image/x-icon");
        fileTypeMap.put("ief", "image/ief");
        fileTypeMap.put("iii", "application/x-iphone");
        fileTypeMap.put("ins", "application/x-internet-signup");
        fileTypeMap.put("isp", "application/x-internet-signup");
        fileTypeMap.put("jfif", "image/pipeg");
        fileTypeMap.put("jpe", "image/jpeg");
        fileTypeMap.put("jpeg", "image/jpeg");
        fileTypeMap.put("jpg", "image/jpeg");
        fileTypeMap.put("js", "application/x-javascript");
        fileTypeMap.put("latex", "application/x-latex");
        fileTypeMap.put("lha", "application/octet-stream");
        fileTypeMap.put("lsf", "video/x-la-asf");
        fileTypeMap.put("lsx", "video/x-la-asf");
        fileTypeMap.put("lzh", "application/octet-stream");
        fileTypeMap.put("m13", "application/x-msmediaview");
        fileTypeMap.put("m14", "application/x-msmediaview");
        fileTypeMap.put("m3u", "audio/x-mpegurl");
        fileTypeMap.put("man", "application/x-troff-man");
        fileTypeMap.put("mdb", "application/x-msaccess");
        fileTypeMap.put("me", "application/x-troff-me");
        fileTypeMap.put("mht", "message/rfc822");
        fileTypeMap.put("mhtml", "message/rfc822");
        fileTypeMap.put("mid", "audio/mid");
        fileTypeMap.put("mny", "application/x-msmoney");
        fileTypeMap.put("mov", "video/quicktime");
        fileTypeMap.put("movie", "video/x-sgi-movie");
        fileTypeMap.put("mp2", "video/mpeg");
        fileTypeMap.put("mp3", "audio/mpeg");
        fileTypeMap.put("mpa", "video/mpeg");
        fileTypeMap.put("mpe", "video/mpeg");
        fileTypeMap.put("mpeg", "video/mpeg");
        fileTypeMap.put("mpg", "video/mpeg");
        fileTypeMap.put("mpp", "application/vnd.ms-project");
        fileTypeMap.put("mpv2", "video/mpeg");
        fileTypeMap.put("ms", "application/x-troff-ms");
        fileTypeMap.put("mvb", "application/x-msmediaview");
        fileTypeMap.put("nws", "message/rfc822");
        fileTypeMap.put("oda", "application/oda");
        fileTypeMap.put("p10", "application/pkcs10");
        fileTypeMap.put("p12", "application/x-pkcs12");
        fileTypeMap.put("p7b", "application/x-pkcs7-certificates");
        fileTypeMap.put("p7c", "application/x-pkcs7-mime");
        fileTypeMap.put("p7m", "application/x-pkcs7-mime");
        fileTypeMap.put("p7r", "application/x-pkcs7-certreqresp");
        fileTypeMap.put("p7s", "application/x-pkcs7-signature");
        fileTypeMap.put("pbm", "image/x-portable-bitmap");
        fileTypeMap.put("pdf", "application/pdf");
        fileTypeMap.put("pfx", "application/x-pkcs12");
        fileTypeMap.put("pgm", "image/x-portable-graymap");
        fileTypeMap.put("pko", "application/ynd.ms-pkipko");
        fileTypeMap.put("pma", "application/x-perfmon");
        fileTypeMap.put("pmc", "application/x-perfmon");
        fileTypeMap.put("pml", "application/x-perfmon");
        fileTypeMap.put("pmr", "application/x-perfmon");
        fileTypeMap.put("pmw", "application/x-perfmon");
        fileTypeMap.put("pnm", "image/x-portable-anymap");
        fileTypeMap.put("pot,", "application/vnd.ms-powerpoint");
        fileTypeMap.put("ppm", "image/x-portable-pixmap");
        fileTypeMap.put("pps", "application/vnd.ms-powerpoint");
        fileTypeMap.put("ppt", "application/vnd.ms-powerpoint");
        fileTypeMap.put("prf", "application/pics-rules");
        fileTypeMap.put("ps", "application/postscript");
        fileTypeMap.put("pub", "application/x-mspublisher");
        fileTypeMap.put("qt", "video/quicktime");
        fileTypeMap.put("ra", "audio/x-pn-realaudio");
        fileTypeMap.put("ram", "audio/x-pn-realaudio");
        fileTypeMap.put("ras", "image/x-cmu-raster");
        fileTypeMap.put("rgb", "image/x-rgb");
        fileTypeMap.put("rmi", "audio/mid");
        fileTypeMap.put("roff", "application/x-troff");
        fileTypeMap.put("rtf", "application/rtf");
        fileTypeMap.put("rtx", "text/richtext");
        fileTypeMap.put("scd", "application/x-msschedule");
        fileTypeMap.put("sct", "text/scriptlet");
        fileTypeMap.put("setpay", "application/set-payment-initiation");
        fileTypeMap.put("setreg", "application/set-registration-initiation");
        fileTypeMap.put("sh", "application/x-sh");
        fileTypeMap.put("shar", "application/x-shar");
        fileTypeMap.put("sit", "application/x-stuffit");
        fileTypeMap.put("snd", "audio/basic");
        fileTypeMap.put("spc", "application/x-pkcs7-certificates");
        fileTypeMap.put("spl", "application/futuresplash");
        fileTypeMap.put("src", "application/x-wais-source");
        fileTypeMap.put("sst", "application/vnd.ms-pkicertstore");
        fileTypeMap.put("stl", "application/vnd.ms-pkistl");
        fileTypeMap.put("stm", "text/html");
        fileTypeMap.put("svg", "image/svg+xml");
        fileTypeMap.put("sv4cpio", "application/x-sv4cpio");
        fileTypeMap.put("sv4crc", "application/x-sv4crc");
        fileTypeMap.put("swf", "application/x-shockwave-flash");
        fileTypeMap.put("t", "application/x-troff");
        fileTypeMap.put("tar", "application/x-tar");
        fileTypeMap.put("tcl", "application/x-tcl");
        fileTypeMap.put("tex", "application/x-tex");
        fileTypeMap.put("texi", "application/x-texinfo");
        fileTypeMap.put("texinfo", "application/x-texinfo");
        fileTypeMap.put("tgz", "application/x-compressed");
        fileTypeMap.put("tif", "image/tiff");
        fileTypeMap.put("tiff", "image/tiff");
        fileTypeMap.put("tr", "application/x-troff");
        fileTypeMap.put("trm", "application/x-msterminal");
        fileTypeMap.put("tsv", "text/tab-separated-values");
        fileTypeMap.put("txt", "text/plain");
        fileTypeMap.put("uls", "text/iuls");
        fileTypeMap.put("ustar", "application/x-ustar");
        fileTypeMap.put("vcf", "text/x-vcard");
        fileTypeMap.put("vrml", "x-world/x-vrml");
        fileTypeMap.put("wav", "audio/x-wav");
        fileTypeMap.put("wcm", "application/vnd.ms-works");
        fileTypeMap.put("wdb", "application/vnd.ms-works");
        fileTypeMap.put("wks", "application/vnd.ms-works");
        fileTypeMap.put("wmf", "application/x-msmetafile");
        fileTypeMap.put("wps", "application/vnd.ms-works");
        fileTypeMap.put("wri", "application/x-mswrite");
        fileTypeMap.put("wrl", "x-world/x-vrml");
        fileTypeMap.put("wrz", "x-world/x-vrml");
        fileTypeMap.put("xaf", "x-world/x-vrml");
        fileTypeMap.put("xbm", "image/x-xbitmap");
        fileTypeMap.put("xla", "application/vnd.ms-excel");
        fileTypeMap.put("xlc", "application/vnd.ms-excel");
        fileTypeMap.put("xlm", "application/vnd.ms-excel");
        fileTypeMap.put("xls", "application/vnd.ms-excel");
        fileTypeMap.put("xlt", "application/vnd.ms-excel");
        fileTypeMap.put("xlw", "application/vnd.ms-excel");
        fileTypeMap.put("xof", "x-world/x-vrml");
        fileTypeMap.put("xpm", "image/x-xpixmap");
        fileTypeMap.put("xwd", "image/x-xwindowdump");
        fileTypeMap.put("z", "application/x-compress");
        fileTypeMap.put("zip", "application/zip");
        //endregion hashmap
    }

    public static FileUtility getInstance() {
        if (fileUtility == null) {
            fileUtility = new FileUtility();
        }
        return fileUtility;
    }

    /**
     * @param extension for example txt. do not use .txt.
     * @return 返回文件类型 image video audio text application x-word message
     */
    public String getType(String extension) {
        String n = fileTypeMap.get(extension);
        if (n != null) {
            return n.substring(0, n.indexOf("/"));
        }
        return null;
    }

    public String getMimeType(String docType) {
        String mime = fileTypeMap.get(docType);
        if (mime == null) {
            mime = "application/octet-stream";
        }
        return mime;
    }
}

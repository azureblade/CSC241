package util;
 
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
 
public class WebDoc {
 
  public static String getBodyContent(String urlstr)
          throws MalformedURLException, IOException {
    /*
     * The following convoluted code is necessary because getParser()
     * is a protected method in HTMLEditorKit.
 
     * We create an anonymous extension of HTMLEditorKit with a public
     * getParser method calling the protected method of the superclass.
     */
    HTMLEditorKit.Parser parser = new HTMLEditorKit() {
 
      @Override
      public HTMLEditorKit.Parser getParser() {
        return super.getParser();
      }
 
    }.getParser();
 
    class DocStatus {
      public String content = "";
      public boolean body_started = false;
    }
 
    final DocStatus status = new DocStatus();
 
    HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback() {
 
      // handle the tags: look for the BODY tag
      @Override
      public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if (t == HTML.Tag.BODY) {
          status.body_started = true;
        }
      }
 
      // handle the text between tags: concatenate all text after BODY tag
      @Override
      public void handleText(char[] text, int position) {
        if (status.body_started) {
          status.content += String.valueOf(text) + " ";
        }
      }
    };
 
    URL url = new URL(urlstr);
 
    InputStreamReader r = new InputStreamReader(url.openStream());
    parser.parse(r, callback, true);
 
    return status.content;
  }
}
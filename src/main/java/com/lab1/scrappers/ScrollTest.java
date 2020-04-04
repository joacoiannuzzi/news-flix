package com.lab1.scrappers;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class ScrollTest {

    public static void main(String[] args) throws IOException {
        WebClient webClient = new WebClient();
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setCssEnabled(false);

        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
        webClient.getOptions().setThrowExceptionOnScriptError(true);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setPopupBlockerEnabled(true);
        webClient.getOptions().setPrintContentOnFailingStatusCode(true);
        webClient.getOptions().setUseInsecureSSL(true);

        HtmlPage page = webClient.getPage("https://www.lanacion.com.ar");

        String javaScriptCode = "window.scrollBy(0,3000);";
        ScriptResult result = page.executeJavaScript(javaScriptCode);
        result.getJavaScriptResult();
        System.out.println("result: " + result);
    }
}

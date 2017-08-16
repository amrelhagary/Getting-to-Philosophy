package com.bento.GettingToPhilosophy.Service;

import com.bento.GettingToPhilosophy.Entity.Link;
import com.bento.GettingToPhilosophy.Entity.Wiki;
import com.bento.GettingToPhilosophy.Repository.WikiRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class WikiService {

    private List<Link> links;
    private int linkCounter;
    private Set visitedLinks;
    @Autowired
    private WikiRepository wikiRepository;

    public WikiService(){
        links = new ArrayList<Link>();
        visitedLinks = new HashSet<String>();
    }

    public Wiki findPhilosophy(String url) throws Exception {

        try{
            if(!isValidUrl(url)){
                throw new Exception("Invalid Wikipedia Url");
            }

            List<Link> links = findPhilosophyLinks(url);
            Wiki article = new Wiki();
            article.setURL(url);
            article.setLinkCount(linkCounter);
            article.setTitle(getTitle(url));
            article.setLinks(links);
            this.wikiRepository.save(article);
            linkCounter = 0;
            return article;
        }catch (Exception e){
            throw e;
        }



    }

    private List<Link> findPhilosophyLinks(String url) throws Exception{
        if(url.equalsIgnoreCase("https://en.wikipedia.org/wiki/philosophy")) {
            // clean up
            List<Link> linkList = links;
            visitedLinks = new HashSet();
            links = new ArrayList<>();
            return linkList;
        }else if(this.linkCounter > 100){
            throw new Exception("Exceed the depth limit");
        }else{
            if(!visitedLinks.contains(url)){
                Link link = new Link();
                String nextLink = getNextLink(url);
                String title = getTitle(nextLink);
                link.setURL(url);
                link.setTitle(title);
                links.add(link);
                linkCounter++;
                // for debugging
                printTitle(title);
                visitedLinks.add(url);
                return findPhilosophyLinks(nextLink);
            }else{
                throw new Exception("Loop Detected");
            }

        }
    }

    public String getNextLink(String article) throws Exception{
        String nextLink = "";
        Document document = Jsoup.connect(article).get();
        Elements docLinks = document.select(".mw-parser-output > p a[href]");
        for(Element link : docLinks){
            String url = link.attr("abs:href");
            if((isFirstRealLink(url))){
                nextLink = url;
                break;
            }
        }
        return nextLink;
    }


    public String getTitle(String article) {
        System.out.print(article);
        String title = article.substring(30).replace("_", " ");
        return title;
    }

    private void printTitle(String title){
        // for debugging
        System.out.println("         ▼\n      " + title);
    }

    public boolean isValidUrl(String input){
        return input.startsWith("https://en.wikipedia.org/wiki/");
    }

    public boolean isFirstRealLink(String link){
        return (link.contains("wiki") && !link.contains("Greek") && !link.contains("Latin") && !link.contains("wiktionary") && !link.contains("#") && !link.contains("Help:"));
    }
}

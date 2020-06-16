package com.amoskorir.kmlparser.kmlutils.models;

public class Snippet {  private String maxLines;

    private String content;

    public String getMaxLines ()
    {
        return maxLines;
    }

    public void setMaxLines (String maxLines)
    {
        this.maxLines = maxLines;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [maxLines = "+maxLines+", content = "+content+"]";
    }
}

import java.awt.FontMetrics;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Font;

public class DrawString
{
	public static void drawText(String text, Font font, Graphics g, int width, int startX, int startY)
	{
		String[] words = text.split(" ");//String method that splits a String phrase into words, since " " is passed in
		int i = 0;//Count integer
		while (i < words.length)
		{
			String currentLine = words[i++];//String that holds the characters that will be printed on the current line
			while (( i < words.length ) && (g.getFontMetrics(font).stringWidth(currentLine + " " + words[i]) < width))//While loop that runs while the pixel width of the string is less than the width passed in
			{
				currentLine = currentLine + " " + words[i];//Adds as many words as fit onto the line
				i++;
			}
			g.drawString(currentLine, startX, startY);//Draws the line
			int lineHeight = g.getFontMetrics(font).getHeight();//Gets the height of a standard line of text in the passed in font
			startY = startY + lineHeight;//Increases the y variable to draw on the next line
		}
	}
}
package fsquare.svg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.app.beans.SVGPanel;
import com.kitfox.svg.xml.StyleAttribute;

public class SampleFrame extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private final SVGPanel svgPanel;
    private final URI svgUri;
    private final String fileName;

    
    public static void main(String[] args) throws Exception
    {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                String[] options = {"Without PNG image (sample_1.svg)", "Embedded PNG image (sample_2.svg)"};
                int sel = JOptionPane.showOptionDialog(null, "Select the SVG", "SVG sample", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                new SampleFrame("sample_" + (sel+1) + ".svg").setVisible(true);
            }
        });

    }

    SampleFrame(String fileName)
    {
        super("SVG sample");
        this.fileName = fileName;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        this.svgUri = getUri();
        this.svgPanel = new SVGPanel();
        this.svgPanel
            .setSvgURI(this.svgUri);
        this.svgPanel.setAntiAlias(true);
        this.svgPanel.setPreferredSize(new Dimension(this.svgPanel.getSVGWidth(), this.svgPanel.getSVGHeight()));
        add(this.svgPanel, BorderLayout.CENTER);
        
        JButton btn = new JButton("Update diagram");
        btn.addActionListener(this);
        add(btn, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        pack();
        
    }

    private URI getUri()
    {
        try
        {
            return getClass().getResource(this.fileName).toURI();
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }   
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        SVGDiagram diagram = this.svgPanel.getSvgUniverse().getDiagram(this.svgUri);
        SVGElement myRect = diagram.getElement("my.rect");
        StyleAttribute hAttr = myRect.getPresAbsolute("height");
        int height = hAttr.getIntValue();
        height = (height>150) ? 120 : 200;
        hAttr.setStringValue("" + height);
        try
        {
            diagram.updateTime(0);
        }
        catch (SVGException e1)
        {
            e1.printStackTrace();
        }
        this.svgPanel.repaint();
        

        
    }

}

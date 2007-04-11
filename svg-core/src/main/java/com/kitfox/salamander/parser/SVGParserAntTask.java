/*
 * GraphicsCodeGenAntTask.java
 *
 * Created on March 3, 2007, 9:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.salamander.parser;

import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.FileScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * This ant task examines a directory hierarchy containing .xml files and
 * for the XML files rooted in the GraphicsCodeGen.GRAPHICS_NS namespace,
 * causes code generation to be run.  Task will only update code that is missing
 * or out of date to minimize processing time.
 *
 * <code>
 * &lt;graphicsCodeGeneration&gt;
 *     &lt;fileset dir="${root.dir}"&gt;
 *         &lt;include name="com/kitfox/monsters/** / *"/&gt;
 *     &lt;/fileset&gt;
 *     &lt;fileset dir="${dir2}"&gt;
 *     &lt;/fileset&gt;
 * &lt;/graphicsCodeGeneration&gt;
 * </code>
 *
 * @author kitfox
 */
public class SVGParserAntTask extends Task
{
//    Path examinePath;
    private ArrayList<FileSet> filesets = new ArrayList<FileSet>();
    
    
    /** Creates a new instance of GraphicsCodeGenAntTask */
    public SVGParserAntTask()
    {
    }
    
    /**
     * Adds a set of files.
     */
    public void addFileset(FileSet set) 
    {
        filesets.add(set);
    }
    
    /**
     * The examine path should be a subset of the classpath and include all the
     * classes that should be examined for possible inclusion in the services list.
     */
//    public Path createExaminePath() throws BuildException
//    {
//        if (examinePath == null)
//        {
//            examinePath = new Path(getProject());
//            return examinePath;
//        }
//        throw new BuildException("Only one examine path can be set");
//    }

    public void execute() throws BuildException
    {
        /*
        //This is necessary for ImageIO to find all pluggable image readers on the
        // classpath
        ImageIO.scanForPlugins();
                                
        for (FileSet fileSet: filesets)
        {
            FileScanner scanner = fileSet.getDirectoryScanner(getProject());
            String[] files = scanner.getIncludedFiles();
            
            File dir = fileSet.getDir();
            
            for (String fileName: files)
            {
//                if (!fileName.getName().endsWith(".xml")) continue;
                File file = new File(dir, fileName);
//System.err.println("*****File " + file);

                //Conditionally generate code
                GraphicsCodeGen.generateCode(file, true);
            }
        }
         */
    }
    
}

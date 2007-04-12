/*
 * ServicesListerAntTask.java
 *
 * Created on September 17, 2006, 4:08 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.kitfox.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.apache.tools.ant.taskdefs.DefBase;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.resources.FileResource;

/**
 * This ant task examines a directory hierarchy containing .class files and
 * constructs lists of all classes that extend a given base class.  This
 * is meant to facilitate the generation of META-INF/services lists
 * for jar files.
 *
 * <code>
 * &lt;servicesLister&gt;
 *     &lt;examinepath&gt;
 *         &lt;pathelement location="${path-to-find-classfiles-to-be-analyzed}"&gt;
 *     &lt;/examinepath&gt;
 *     &lt;classpath&gt;
 *         &lt;pathelement location="${classpath-needed-to-construct-Class-objects-of-classes-in-examine-path}"&gt;
 *     &lt;/classpath&gt;
 *     &lt;service className="name.of.class.to.build.list.For1" toDir="location/to/save/generated.list1"&gt;
 *     &lt;service className="name.of.class.to.build.list.For2" toDir="location/to/save/generated.list2"&gt;
 * &lt;/servicesLister&gt;
 * </code>
 *
 * @author kitfox
 */
public class ServicesListerAntTask extends DefBase
{
    /**
     * Contains info for a single service to generate a list for.  There may be 
     * zero or more services tags embedded in the servicesLister task.
     *
     * If the toFile parameter is set to the name of a directory, the output
     * file name is computed at new File(toFile, className)
     */
    public class Service
    {
        private String className;
        private File toFile;
        private File toDir;
        private Class targetClass;

        ArrayList<String> targets = new ArrayList<String>();
        
        public String getClassName()
        {
            return className;
        }

        public void setClassName(String className)
        {
            this.className = className;
        }

        public File getToFile()
        {
            return toFile;
        }

        public void setToFile(File toFile)
        {
            this.toFile = toFile;
        }

        public File getToDir()
        {
            return toDir;
        }

        public void setToDir(File toDir)
        {
            if (toDir.exists() && !toDir.isDirectory())
            {
                throw new BuildException("toDir argument must be a directory: " + toDir);
            }
            this.toDir = toDir;
        }

        public Class getTargetClass()
        {
            return targetClass;
        }

        public void setTargetClass(Class targetClass)
        {
            this.targetClass = targetClass;
        }

        private void writeTarget(String className)
        {
            targets.add(className);
        }

        private void writeToFile()
        {
            try
            {
                if (toFile != null)
                {
                    if (toFile.exists() && toFile.isDirectory())
                    {
                        toFile = new File(toFile, className);
                    }
                }
                else
                {
                    if (toDir == null)
                    {
                        throw new BuildException("Must specify either a toFile or a toDir parameter");
                    }
                    toFile = new File(toDir, className);
                }
                
                
                //Make sure parent directory exists
                File parent = toFile.getParentFile();
                if (!parent.exists())
                {
                    parent.mkdirs();
                }
                
                FileWriter fout = new FileWriter(toFile);
                PrintWriter pw = new PrintWriter(new BufferedWriter(fout));

                pw.println("#Automatically generated services list");
                pw.println("#Mark McKay - " + new Date());

                for (String target: targets)
                {
                    pw.println(target);
                }
                
                pw.close();
            }
            catch (IOException e)
            {
                throw new BuildException(e);
            }
        }
    }
    
    Path examinePath;
//    ResourceCollection targetRes;
    ArrayList<Service> services = new ArrayList<Service>();
    
    /**
     * The examine path should be a subset of the classpath and include all the
     * classes that should be examined for possible inclusion in the services list.
     */
    public Path createExaminePath() throws BuildException
    {
        if (examinePath == null)
        {
            examinePath = new Path(getProject());
            return examinePath;
        }
        throw new BuildException("Only one examine path can be set");
    }

    /*
    public void addFileSet(FileSet fileSet)
    {
        if (this.targetRes != null)
        {
            throw new BuildException("Resources to be examined set multiple times");
        }
        this.targetRes = fileSet;
    }
     */
    
    public Service createService()
    {
        Service service = new Service();
        services.add(service);
        return service;
    }
    
    /**
     * Creates a new instance of ServicesListerAntTask
     */
    public ServicesListerAntTask()
    {
    }
    
    static final FilenameFilter FFILTER_CLASS = new FilenameFilter()
    {
        public boolean accept(File dir, String name)
        {
            return name.endsWith(".class");
        }
    };
    
    public void scanPath(ClassLoader loader, File dir, File pathRoot)
    {
        String pathAbs = pathRoot.getAbsolutePath();
//System.err.println("***Abs path: " + pathAbs);
//        File[] fileList = dir.listFiles(FFILTER_CLASS);
        for (File file: dir.listFiles())
        {
            if (file.getName().endsWith(".class"))
            {
                String fileAbs = file.getAbsolutePath();
//System.err.println("***Abs file: " + fileAbs);
                String relName = fileAbs.substring(pathAbs.length() + 1, fileAbs.length() - ".class".length());
                relName = relName.replaceAll("[/\\\\]", ".");
//System.err.println("***Rel name: " + relName);
                Class cls;
                try
                {
                    cls = loader.loadClass(relName);
                } 
                catch (ClassNotFoundException ex)
                {
                    throw new BuildException(ex);
                }

                if (cls == null)
                {
                    log("Could not examine class " + file);
                    continue;
                }
                else 
                {
                    //Ignore classes that cannot be instantiated
                    int mod = cls.getModifiers();
                    if (!Modifier.isPublic(mod) || Modifier.isAbstract(mod) || Modifier.isInterface(mod))
                    {
                        continue;
                    }
                }

                for (Service service: services)
                {
                    if (service.getTargetClass().isAssignableFrom(cls))
                    {
                        service.writeTarget(relName);
                    }
                }
            }
            else if (file.isDirectory())
            {
                scanPath(loader, file, pathRoot);
            }
        }
    }
    
    public void execute() throws BuildException
    {
        if (examinePath == null)
        {
            throw new BuildException("Examine path not set - please specify path to files to check for services");
        }

        ClassLoader loader = createLoader();
        
        for (Service service: services)
        {
            String name = service.getClassName();
            Class cls;
            try
            {
                cls = loader.loadClass(name);
            } 
            catch (ClassNotFoundException ex)
            {
                throw new BuildException(String.format("Could not find %s in classpath", name), ex);
            }
            service.setTargetClass(cls);
        }
        
        
        for (Iterator it = examinePath.iterator(); it.hasNext();)
        {
            Resource res = (Resource)it.next();
            if (!(res instanceof FileResource)) continue;
            
//System.err.println("*****Resource " + res.getName() + ", " + res.getClass().getName() + ", " + res.toString());
            File root = ((FileResource)res).getFile();
//System.err.println("*****File " + Name() + " " + res);

            //Find all .class files under the path
            scanPath(loader, root, root);
        }
        

        //Write all to disk
        for (Service service: services)
        {
            service.writeToFile();
        }        
        
    }
    
}

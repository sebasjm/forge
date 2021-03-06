/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.forge.spec.jsf;

import java.io.File;

import javax.inject.Named;

import org.jboss.seam.forge.project.constraints.RequiresFacets;
import org.jboss.seam.forge.project.facets.BaseFacet;
import org.jboss.seam.forge.project.facets.WebResourceFacet;
import org.jboss.seam.forge.project.resources.FileResource;
import org.jboss.seam.forge.project.resources.builtin.DirectoryResource;
import org.jboss.seam.forge.spec.servlet.ServletFacet;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@Named("forge.spec.jsf")
@RequiresFacets({ ServletFacet.class })
public class FacesFacet extends BaseFacet
{
   public FileResource<?> getConfigFile()
   {
      DirectoryResource webRoot = project.getFacet(WebResourceFacet.class).getWebRootDirectory();
      return (FileResource<?>) webRoot.getChild("WEB-INF" + File.separator + "faces-config.xml");
   }

   /*
    * Facet Methods
    */
   @Override
   public boolean isInstalled()
   {
      return getConfigFile().exists();
   }

   @Override
   public boolean install()
   {
      if (!isInstalled())
      {
         // Create faces-config
         if (!getConfigFile().createNewFile())
         {
            throw new RuntimeException("Failed to create required [" + getConfigFile().getFullyQualifiedName() + "]");
         }
         getConfigFile().setContents(getClass()
                  .getResourceAsStream("/org/jboss/seam/forge/web/faces-config.xml"));

         // Set up index redirect
         WebResourceFacet web = project.getFacet(WebResourceFacet.class);
         FileResource<?> index = web.getWebResource("index.html");
         if (!index.exists())
         {
            index.createNewFile();
            project.getFacet(ServletFacet.class).getConfig().getWelcomeFiles().add("index.html");
         }
         index.setContents(getClass()
                  .getResourceAsStream("/org/jboss/seam/forge/jsf/index.html"));

         // Set up JSF index page
         index = web.getWebResource("index.xhtml");
         if (!index.exists())
         {
            index.createNewFile();
            index.setContents(getClass().getResourceAsStream("/org/jboss/seam/forge/jsf/index.xhtml"));
         }

         // Add template
         FileResource<?> template = web.getWebResource("/resources/forge-template.xhtml");
         if (!template.exists())
         {
            template.createNewFile();
            template.setContents(getClass().getResourceAsStream("/org/jboss/seam/forge/jsf/forge-template.xhtml"));
         }

         // Add css
         FileResource<?> css = web.getWebResource("/resources/forge.css");
         if (!css.exists())
         {
            css.createNewFile();
            css.setContents(getClass().getResourceAsStream("/org/jboss/seam/forge/jsf/forge.css"));
         }

         // Add favicon
         FileResource<?> favicon = web.getWebResource("/resources/favicon.ico");
         if (!favicon.exists())
         {
            favicon.createNewFile();
            favicon.setContents(getClass().getResourceAsStream("/org/jboss/seam/forge/web/favicon.ico"));
         }

      }
      project.registerFacet(this);
      return true;
   }
}

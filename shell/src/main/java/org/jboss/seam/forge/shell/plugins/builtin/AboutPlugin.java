/*
 * JBoss, by Red Hat.
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

package org.jboss.seam.forge.shell.plugins.builtin;

import org.jboss.seam.forge.shell.plugins.*;
import org.jboss.seam.forge.shell.util.ShellColor;

import javax.inject.Named;

@Named("about")
@Topic("Shell Environment")
@Help("Display information about this shell.")
public class AboutPlugin implements Plugin
{
   @DefaultCommand
   public void run(PipeOut out)
   {
      out.println("   ____                          _____                    ");
      out.println("  / ___|  ___  __ _ _ __ ___    |  ___|__  _ __ __ _  ___ ");
      out.println("  \\___ \\ / _ \\/ _` | '_ ` _ \\   | |_ / _ \\| '__/ _` |/ _ \\  " + out.renderColor(ShellColor.YELLOW, "\\\\"));
      out.println("   ___) |  __/ (_| | | | | | |  |  _| (_) | | | (_| |  __/  " + out.renderColor(ShellColor.YELLOW, "//"));
      out.println("  |____/ \\___|\\__,_|_| |_| |_|  |_|  \\___/|_|  \\__, |\\___| ");
      out.println("                                                |___/      ");
      out.println("");
      String version = getClass().getPackage().getImplementationVersion();
      out.println("Seam Forge, version [ " + version +" ] - JBoss, by Red Hat, Inc. [ http://jboss.org ]" );
   }
}

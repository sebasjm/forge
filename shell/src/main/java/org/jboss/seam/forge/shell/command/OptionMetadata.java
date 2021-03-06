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

package org.jboss.seam.forge.shell.command;

import org.jboss.seam.forge.shell.PromptType;
import org.jboss.seam.forge.shell.completer.CommandCompleter;
import org.jboss.seam.forge.shell.completer.NullCommandCompleter;
import org.jboss.seam.forge.shell.util.Types;
import org.mvel2.util.ParseTools;
import org.mvel2.util.StringAppender;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class OptionMetadata
{
   private CommandMetadata parent;
   private Class<?> type;
   private int index;
   private int effectiveIndex;

   private String name = "";
   private String shortName = "";
   private String defaultValue = "";
   private String description = "";
   private String help = "";

   private boolean flagOnly = false;
   private boolean required = false;
   private PromptType promptType;

   private boolean pipeOut;
   private boolean pipeIn;
   private Class<? extends CommandCompleter> completerType;

   /**
    * Get an informational string describing this Option
    */
   public String getOptionDescriptor()
   {
      StringAppender appender = new StringAppender("[");
      if (isNamed())
      {
         appender.append(name).append("=");
      }

      if (getDescription().equals(""))
      {
         appender.append("ARG").append(" (").append(Types.getTypeDescriptor(type)).append(")");
      }
      else
      {
         appender.append(description).append(" (").append(Types.getTypeDescriptor(type)).append(")");
      }

      return appender.append(']').toString();
   }

   /**
    * Return whether this option is to be mapped via name or via parameter
    * order.
    */
   public boolean isNamed()
   {
      return (name != null) && !"".equals(name);
   }

   public Class<?> getBoxedType()
   {
      return ParseTools.boxPrimitive(getType());
   }

   public Class<?> getType()
   {
      return type;
   }

   public void setType(final Class<?> type)
   {
      this.type = type;
   }

   public String getName()
   {
      return name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getShortName()
   {
      return shortName;
   }

   public void setShortName(String shortName)
   {
      this.shortName = shortName;
   }

   public boolean isFlagOnly()
   {
      return flagOnly;
   }

   public void setFlagOnly(boolean flagOnly)
   {
      this.flagOnly = flagOnly;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public int getIndex()
   {
      return index;
   }

   public void setIndex(final int index)
   {
      this.index = index;
   }

   public int getEffectiveIndex()
   {
      return effectiveIndex;
   }

   public void setEffectiveIndex(int effectiveIndex)
   {
      this.effectiveIndex = effectiveIndex;
   }

   public String getHelp()
   {
      return help;
   }

   public void setHelp(final String help)
   {
      this.help = help;
   }

   public boolean isRequired()
   {
      return required;
   }

   public void setRequired(final boolean required)
   {
      this.required = required;
   }

   @Override
   public String toString()
   {
      return name + ":" + description;
   }

   public CommandMetadata getParent()
   {
      return parent;
   }

   public void setParent(final CommandMetadata parent)
   {
      this.parent = parent;
   }

   public boolean isBoolean()
   {
      return (Boolean.TYPE.equals(getType()) || Boolean.class.equals(getType()));
   }

   public boolean isVarargs()
   {
      return getType().isArray();
   }

   public String getDefaultValue()
   {
      return defaultValue;
   }

   public void setDefaultValue(final String defaultValue)
   {
      this.defaultValue = defaultValue;
   }

   public boolean hasDefaultValue()
   {
      return (defaultValue != null) && !"".equals(defaultValue);
   }

   public PromptType getPromptType()
   {
      return promptType;
   }

   public void setPromptType(PromptType type)
   {
      this.promptType = type;
   }

   public boolean isPipeOut()
   {
      return pipeOut;
   }

   public void setPipeOut(boolean pipeOut)
   {
      this.pipeOut = pipeOut;
   }

   public boolean isPipeIn()
   {
      return pipeIn;
   }

   public void setPipeIn(boolean pipeIn)
   {
      this.pipeIn = pipeIn;
   }

   public boolean notOrdered() {
      return pipeIn || pipeOut || isNamed();
   }

   public boolean hasCustomCompleter()
   {
      return completerType != null && !completerType.equals(NullCommandCompleter.class);
   }

   public void setCompleterType(Class<? extends CommandCompleter> type)
   {
      this.completerType = type;
   }

   public Class<? extends CommandCompleter> getCompleterType()
   {
      return completerType;
   }
}

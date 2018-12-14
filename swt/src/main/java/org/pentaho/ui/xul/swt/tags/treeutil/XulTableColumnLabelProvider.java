/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags.treeutil;

import java.util.Vector;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.swt.tags.SwtTreeItem;
import org.pentaho.ui.xul.util.SwtXulUtil;

public class XulTableColumnLabelProvider implements ITableLabelProvider {

  private XulTree tree;
  private XulDomContainer domContainer;

  public XulTableColumnLabelProvider( XulTree tree, XulDomContainer aDomContainer ) {
    this.tree = tree;
    this.domContainer = aDomContainer;
  }

  public String getColumnText( Object obj, int i ) {

    XulTreeCell cell = ( (XulTreeItem) obj ).getRow().getCell( i );
    if ( cell == null ) {
      return "";
    }

    switch ( tree.getColumns().getColumn( i ).getColumnType() ) {
      case CHECKBOX:
        return cell.getLabel() != null ? cell.getLabel() : cell.getLabel();
      case COMBOBOX:
      case EDITABLECOMBOBOX:
        Vector<?> vec = (Vector<?>) cell.getValue();
        if ( vec != null && vec.size() > cell.getSelectedIndex() ) {
          return "" + vec.get( cell.getSelectedIndex() );
        } else {
          return "";
        }
      case TEXT:
        return cell.getLabel() != null ? cell.getLabel() : "";
      case PASSWORD:
        return getPasswordString( cell.getLabel().length() );
      default:
        return cell.getLabel() != null ? cell.getLabel() : "";
    }

  }

  private String getPasswordString( int len ) {
    StringBuilder b = new StringBuilder();
    while ( len-- > 0 ) {
      b.append( '*' );
    }
    return b.toString();
  }

  public void addListener( ILabelProviderListener ilabelproviderlistener ) {
  }

  public void dispose() {
  }

  public boolean isLabelProperty( Object obj, String s ) {
    return false;
  }

  public void removeListener( ILabelProviderListener ilabelproviderlistener ) {
  }

  public Image getColumnImage( Object row, int col ) {
    if ( tree.getColumns().getColumn( col ).getImagebinding() != null ) {
      String src = ( (SwtTreeItem) row ).getImage();
      Display display = ( (TableViewer) tree.getManagedObject() ).getTable().getDisplay();
      return SwtXulUtil.getCachedImage( src, domContainer, display );
    }
    return null;
  }

}

/*********************************************************************
*
*      Copyright (C) 2002 Andrew Khan
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
***************************************************************************/

package jxl.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;

import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Goes through each cell in the workbook, and if the cell has any features
 * associated with, it prints out the cell contents and the features
 */
public class Features
{
  /**
   * Constructor
   *
   * @param w The workbook to interrogate
   * @param out The output stream to which the CSV values are written
   * @param encoding The encoding used by the output stream.  Null or
   * unrecognized values cause the encoding to default to UTF8
   * @exception IOException
   */
  public Features(Workbook w, OutputStream out, String encoding)
    throws IOException
  {
    if (encoding == null || !encoding.equals("UnicodeBig"))
    {
      encoding = "UTF8";
    }

    try
    {
      OutputStreamWriter osw = new OutputStreamWriter(out, encoding);
      BufferedWriter bw = new BufferedWriter(osw);

      for (int sheet = 0; sheet < w.getNumberOfSheets(); sheet++)
      {
        Sheet s = w.getSheet(sheet);

        bw.write(s.getName());
        bw.newLine();

        Cell[] row = null;
        Cell c = null;

        for (int i = 0 ; i < s.getRows() ; i++)
        {
          row = s.getRow(i);

          for (int j = 0; j < row.length; j++)
          {
            c = row[j];
            if (c.getCellFeatures() != null)
            {
              CellFeatures features = c.getCellFeatures();
              StringBuffer sb = new StringBuffer();
              CellReferenceHelper.getCellReference
                 (c.getColumn(), c.getRow(), sb);

              bw.write("Cell "  + sb.toString() +
                       " contents:  " + c.getContents());
              bw.flush();
              bw.write(" comment: " + features.getComment());
              bw.flush();
              bw.newLine();
            }
          }
        }
      }
      bw.flush();
      bw.close();
    }
    catch (UnsupportedEncodingException e)
    {
      System.err.println(e.toString());
    }
  }

}


// ===================================================================================================================================
//                                           ...... ...
//                                        .,...........                   Permission is hereby granted, free of charge, to any person
//                               . .:::,,::,. .... .                      obtaining a copy of this software and associated documentation
//                         .     ,;:,..,::,. . ...                        files (the "Software"), to deal in the Software without
//                      .,=iiiitt: . ,::,,. ...                           restriction, including without limitation the rights to
//                  .=tR#WY;,tMYI#R=;:,. .,..,,,,,,,,,.,,,,....           use, copy, modify, merge, publish, distribute, sublicense,
//             ;=,  X#I,t##V+iM#WBi;:...;=:;,.....,.,     .......         and/or sell copies of the Software, and to permit persons
//           ;tVWBXVMMVItB#MBM##X++i+++t;.      ...,,.,...     ....       to whom the Software is furnished to do so, subject to the
//          .IVYWVY:tYMXYRW##WBt;;+i==++;:=;==;:,,......      . ...       following conditions:
//          ,XYR#YI.+YBWMWWBY=   ,.   ,,:;+==;:   .   ...........
//           tR##YIYVYVWWWXi,;+++++=++i+i+++++++= ,,,,.                   1) The copyright notice and this permission notice shall
//           ,BV##IYYIRWMBBW##W#WM##BBBWBRBBBM##B,                           be included in all copies or substantial portions of the
//            i==VMMBBRWMBWWYYWWWXIYBMWi+BMItXXIVX                           Software.
//          . =Y,iYXWX.MBM#XiXWWXiYtBWYiVWMBtYRtIB;
//         . .XBY=tBMtYBRRMI+BMt=YYiYBXMt,;YYiRBMB:                       THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
//          .iR.+XXX=;RRRVi+IXXXBY.tYWM;    ;+ItIi                        EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
//        . :MX  Y+  YY.;RXXR;,IX  III,     ,i+,                          OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
//       . +BX, ;V  :V+  .tX=     ,Iit       I+i                          NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
//     :YYIXi  .;+: tY,  .        ,IIt      .III                          HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
//     .:+=.      .,tt .           YXI.      :Rt.                         WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//                 .II.            :R;       .;Bi                         FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
//                 :II.             +X,         =YI=                      OTHER DEALINGS IN THE SOFTWARE.
//                  IB,              YX.          :XV;=:
//                 :V+                IR;          .,=X;.                 EXPECT BUGS.
//                =XX,                 iXt            ..
//             , ;BX:                   =VI:
//            ,YRB+                     . :VI                             Author: Henrik Springfors (moliate@yahoo.se)
//           .==;;                         ;=                             Copyright 2003
//          .  .                          .  .
// ===================================================================================================================================
package info.esblurock.react.common;
import  java.text.ParseException;

/**
 * IParsableElement is an interface of all parsable elements. 
 *
 * @author moliate
 */
public interface IParsableElement
{
        /**
         * parse is the method that does the parsing. Usually it is implemented in
         * a subclass of the class that actually contains the data.
         * @param data the input as a string of bytes. For text files this usually comes from String.getBytes()
         */
	void parse(byte[] data) throws ParseException;
        /**
         * Prints an instance of the element, usually to Log.CRITICAL
         */
	void print();
        /**
         * Similar to the clone() method of objects, this method is included to allow the setting of 
         * data on an abstract baseclass.
         */
	void setData(IParsableElement element);
}

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
/*
 * ReactMechanismGeneration.java
 *
 * Created on February 26, 2004, 12:35 PM
 */

package info.esblurock.react.mechanisms;
import info.esblurock.react.common.IParsableElement;
import info.esblurock.react.common.ReactionLog;
import java.util.StringTokenizer;
/**
 *
 * @author  reaction
 * @version 
 */
public abstract class ReactMechanismGeneration implements IParsableElement
{
    
        public String mechanismName = new String("MechanismGeneration");   
        public ReactMechanismGenerationStep[] Steps = {};    
        public String[] initialMolecules = {};

        public String toString() 
        {
            StringBuffer sb = new StringBuffer();
            sb.append("%% MechanismGeneration: ");
            sb.append(mechanismName);
            sb.append("\n");
            sb.append(mechanismName);
            sb.append("\n");
            sb.append("%%   Molecules\n");

            for(int i = 0; i < initialMolecules.length; i++) {
                sb.append(initialMolecules[i]);
                if(i != initialMolecules.length - 1) 
                        sb.append(",\\");
                sb.append("\n");
            }
            sb.append("%% Mechanism Steps\n");

            for(int j = 0; j < Steps.length; j++) {
                sb.append(Steps[j].toString());
                sb.append("\n");
            }
            
            return sb.toString();
        }
        
        
        public void print() 
        {
            System.out.println(this.toString());
            ReactionLog.logInfo(this.toString());
        }
        
        public void setData(IParsableElement element) 
        {
            if (! (element instanceof ReactMechanismGeneration) )
            {   
                ReactionLog.logError(" > Tried to parse an element of wrong type: " + element.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
                return;
            }

            ReactMechanismGeneration e = (ReactMechanismGeneration)element;
            initialMolecules = e.initialMolecules;
            mechanismName = e.mechanismName;
            Steps = e.Steps; 
            for (int i = 0; i < Steps.length; i++)
            {
                Steps[i].setData(e.Steps[i]);
            }
        }
        
}

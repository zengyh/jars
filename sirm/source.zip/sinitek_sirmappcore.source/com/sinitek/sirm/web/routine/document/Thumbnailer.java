// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Thumbnailer.java

package com.sinitek.sirm.web.routine.document;

import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleOp;
import com.sinitek.sirm.common.web.SirmAction;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

// Referenced classes of package com.sinitek.sirm.web.routine.document:
//            MyDemoServlet

public class Thumbnailer extends SirmAction
{

    public Thumbnailer()
    {
    }

    public String thumbnailer()
        throws IOException
    {
        int width = StringUtils.isEmpty(request.getParameter("w")) ? 150 : Integer.valueOf(request.getParameter("w")).intValue();
        String path = request.getParameter("p");
        path = FilenameUtils.normalize(path);
        File f = new File(MyDemoServlet.HOME_SHARED_DOCS, path);
        Boolean real = Boolean.valueOf(request.getParameter("r") != null);
        BufferedImage b = null;
        if(real.booleanValue())
        {
            b = ImageIO.read(f);
            ImageIO.write(b, "png", response.getOutputStream());
            return null;
        } else
        {
            BufferedImage image = ImageIO.read(f);
            ResampleOp rop = new ResampleOp(DimensionConstrain.createMaxDimension(width, -1));
            rop.setNumberOfThreads(4);
            b = rop.filter(image, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(b, "png", baos);
            byte bytesOut[] = baos.toByteArray();
            response.setHeader("Last-Modified", DateUtils.addDays(Calendar.getInstance().getTime(), 720).toGMTString());
            response.setHeader("Expires", DateUtils.addDays(Calendar.getInstance().getTime(), 720).toGMTString());
            ImageIO.write(b, "png", response.getOutputStream());
            return null;
        }
    }
}

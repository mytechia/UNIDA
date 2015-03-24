package com.hi3project.unida.library.location;

import com.mytechia.commons.util.draw.MyPoint;


/**
 * <p><b>
 * </b>
 *
 *
 *
 * <p><b>Creation date:</b> 25-01-2010</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 25-01-2010 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela
 * @version 1
 */
public class StringLiteralRelativeLocation extends StringLiteralLocation
{


    private MyPoint relativeLocation;

    
    public StringLiteralRelativeLocation(String location, MyPoint relativeLocation)
    {
        super(location);
        this.relativeLocation = relativeLocation;
    }


    public StringLiteralRelativeLocation(Long codId, String location, MyPoint relativeLocation)
    {
        super(codId, location);
        this.relativeLocation = relativeLocation;
    }

    
    public MyPoint getRelativeLocation()
    {
        return relativeLocation;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StringLiteralRelativeLocation other = (StringLiteralRelativeLocation) obj;
        if (this.relativeLocation != other.relativeLocation && (this.relativeLocation == null || !this.relativeLocation.equals(other.relativeLocation))) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 79 * hash + (this.relativeLocation != null ? this.relativeLocation.hashCode() : 0);
        return hash;
    }


}

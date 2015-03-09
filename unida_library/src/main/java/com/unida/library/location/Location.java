package com.unida.library.location;

import java.io.Serializable;

/**
 * <p><b>Description:</b></p>
 * 
 *
 *
 *
 * <p><b>Creation date:</b> 28-dic-2009</p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li>1 - 28-dic-2009 Initial release</li>
 * </ul>
 *
 *
 * @author Gervasio Varela Fernandez
 * @version 1
 */
public abstract class Location implements Serializable
{

    private Long codId;

    
    public Location(Long codId)
    {
        this.codId = codId;
    }


    public Location()
    {
        
    }


    public Long getId()
    {
        return this.codId;
    }


    @Override
    public abstract String toString();

    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.codId != other.codId && (this.codId == null || !this.codId.equals(other.codId))) {
            return false;
        }
        return true;
    }

    
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 23 * hash + (this.codId != null ? this.codId.hashCode() : 0);
        return hash;
    }

}

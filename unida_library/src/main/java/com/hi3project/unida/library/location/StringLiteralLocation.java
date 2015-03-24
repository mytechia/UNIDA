package com.hi3project.unida.library.location;

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
public class StringLiteralLocation extends Location
{

    
    private String location;

    
    
    public StringLiteralLocation(Long codId, String location)
    {
        super(codId);
        this.location = location;
    }


    public StringLiteralLocation(String location)
    {
        super(null);
        this.location = location;
    }


    public StringLiteralLocation()
    {
        
    }


    public String getLocation() {
        return this.location;
    }

    @Override
    public String toString()
    {
        return this.location;
    }



}

/*******************************************************************************
 *   
 *   Copyright 2014 Mytech Ingenieria Aplicada <http://www.mytechia.com>
 *   Copyright 2014 Victor Sonora Pombo
 * 
 ******************************************************************************/

package com.hi3project.unida.protocol.message.notification;

/**
 * <p><b>Description:</b></p>
 *
 *
 * <p><b>Creation date:</b> 
 * 07-10-2014 </p>
 *
 * <p><b>Changelog:</b></p>
 * <ul>
 * <li> 1 , 07-10-2014 - Initial release</li>
 * </ul>
 *
 * @author Victor Sonora Pombo
 * @version 1
 */
public enum TriggerCurrentStateEnum 
{

    FALSE       ((byte) 0x00),
    TRUE        ((byte) 0x01),
    UNDEFINED   ((byte) 0x02);
    
    private byte typeValue;
    
    
    
    TriggerCurrentStateEnum(byte typeValue) {
        this.typeValue = typeValue;
    }
   
    
    
    public byte getTypeValue() {
        return typeValue;
    }
    

    public static TriggerCurrentStateEnum getTypeOf(byte typeValue) {
        if (typeValue == FALSE.typeValue)
            return FALSE;
        else if (typeValue == TRUE.typeValue)
            return TRUE;
        else if (typeValue == UNDEFINED.typeValue)
            return UNDEFINED;
        else
            return null;
    }
    
    public static TriggerCurrentStateEnum valueFor(boolean value)
    {
        return value?TRUE:FALSE;
    }
    
    public boolean asBoolean()
    {
        return (this.typeValue == TRUE.getTypeValue());
    }
    
    @Override
    public String toString()
    {
        return "TriggerState{" + "typeValue=" + typeValue + '}';
    }
    
}

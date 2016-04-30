package an0nym8us.api.messaging;

/** Defines packet patterns
 * @author An0nym8us
 *
 */
public enum PacketStruct
{
	Handshake(0x00, "serverName", "secretKey", "IV"),
	Server(0x01, "channel", "serverFrom", "serverTo"),
	DuplicatedConnection(0x02);
	
	public int packetID;
	public String[] params;
	
	PacketStruct(int packetID, String... params)
	{
		this.packetID = packetID;
		this.params = params;
	}
	
	public static PacketStruct GetPacketByID(int packetID)
	{
		for(PacketStruct packetStruct : PacketStruct.values())
		{
			if(packetStruct.packetID == packetID)
			{
				return packetStruct;
			}
		}
		
		return null;
	}
	
	public int GetParamIndex(String param)
	{
		for(int i = 0; i < params.length; i++)
		{
			if(params[i].equals(param))
			{
				return i;
			}
		}
		
		return -1;
	}
}

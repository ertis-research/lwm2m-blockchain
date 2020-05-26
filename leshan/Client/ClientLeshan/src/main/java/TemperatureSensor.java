import java.util.Random;

import org.eclipse.leshan.client.request.ServerIdentity;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.response.ReadResponse;

public class TemperatureSensor extends BaseInstanceEnabler{
	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		System.out.println("ReadResponse "+resourceid + " " +identity.getPskIdentity());
		switch (resourceid) {
		case 0:
			return ReadResponse.success(resourceid, getRandom());
		}
		return ReadResponse.notFound();
	}
	
	private int getRandom() {
		Random rand = new Random();
		return rand.nextInt(60);
	}
}

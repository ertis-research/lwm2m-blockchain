import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.eclipse.leshan.client.request.ServerIdentity;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.response.ReadResponse;

public class TemperatureSensor extends BaseInstanceEnabler{
		
	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		System.out.println("ReadResponse "+resourceid + " " +identity.getPskIdentity());
		switch (resourceid) {
		case 5700:
			return ReadResponse.success(resourceid, getTwoDigitValue(getRandom()));
		}
		return ReadResponse.notFound();
	}
	
	private double getRandom() {
		double leftLimit = 1;
	    double rightLimit = 60;
	    double generatedRandom = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
		return generatedRandom;
	}
	
	private double getTwoDigitValue(double value) {
        BigDecimal toBeTruncated = BigDecimal.valueOf(value);
        return toBeTruncated.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
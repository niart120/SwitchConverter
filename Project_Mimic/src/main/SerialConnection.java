package main;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialConnection{

	private OutputStream os;
	private Command cmd;
	private ScheduledExecutorService scheduler;
	private ScheduledFuture<?> future;

	public SerialConnection(Command cmd) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.cmd = cmd;
		try {
			SerialPort port = (SerialPort)CommPortIdentifier.getPortIdentifier("COM10").open("Switch Controller",2000);
			port.setSerialPortParams(19200,SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE );
			os = port.getOutputStream();

		} catch (PortInUseException | NoSuchPortException |UnsupportedCommOperationException|IOException e) {
			e.printStackTrace();
		}
		scheduler =Executors.newSingleThreadScheduledExecutor();

	}
	public void start() {
		scheduler.scheduleAtFixedRate(()->{
			try {
				int btn0 = cmd.cmd[0]>>8;
				int btn1 = cmd.cmd[0]&0x00FF;
				os.write(btn0&0x00F0);
				os.write(btn0<<4);
				os.write(btn1&0x00F0);
				os.write(btn1<<4);

				for(int i=1;i<6;i++) {
					int c = cmd.cmd[i];
					os.write(c&0x00F0);
					os.write(c<<4);
				}
				os.write('\r');
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}, 0, 10, TimeUnit.MILLISECONDS);
	}

	public void stop() {
		future.cancel(true);
	}

}

#include <SwitchControlLibrary.h>
int buff[14];
int counter = 0;
void parse(){
  uint16_t btn = uint16_t(buff[0]<<8|buff[1]<<4|buff[2]|buff[3]>>4);
  uint8_t hat = uint8_t(buff[4]|buff[5]>>4);
  uint8_t lx = uint8_t(buff[6]|buff[7]>>4);
  uint8_t ly = uint8_t(buff[8]|buff[9]>>4);
  uint8_t rx = uint8_t(buff[10]|buff[11]>>4);
  uint8_t ry = uint8_t(buff[12]|buff[13]>>4);
  SwitchControlLibrary().MakeReport(btn,hat,lx,ly,rx,ry);
}

void setup() {
  // put your setup code here, to run once:
  Serial1.begin(19200);
  SwitchControlLibrary().PressButtonL();
  SwitchControlLibrary().PressButtonR();
  delay(1000);
  SwitchControlLibrary().ReleaseButtonL();
  SwitchControlLibrary().ReleaseButtonR();
  delay(100);
  SwitchControlLibrary().PressButtonL();
  SwitchControlLibrary().PressButtonR();
  delay(1000);
  SwitchControlLibrary().ReleaseButtonL();
  SwitchControlLibrary().ReleaseButtonR();
  delay(100);
  SwitchControlLibrary().PressButtonL();
  SwitchControlLibrary().PressButtonR();
  delay(1000);
  SwitchControlLibrary().ReleaseButtonL();
  SwitchControlLibrary().ReleaseButtonR();
  delay(100);
}

void loop() {
  // put your main code here, to run repeatedly:
  for(int i;i<5;i++){
    SwitchControlLibrary().PressButtonL();
    SwitchControlLibrary().PressButtonR();
    delay(1000);
    SwitchControlLibrary().ReleaseButtonL();
    SwitchControlLibrary().ReleaseButtonR();
    delay(100);
  }
  while(1){
    if(Serial1.available()){
      int t = Serial1.read();
      if (t!='\r'){
        buff[counter++] = t;
      }else{
        parse();
        counter=0;
      }
    }
  }
}

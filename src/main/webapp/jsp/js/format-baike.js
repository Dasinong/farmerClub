
			function formatTips(tips){
			    var head1 = tips.substring(0,1);
				i = 1;
				if(head1 == "●")
					while(tips.match("●")){
						tips = tips.replace(/●/,String(i).toString()+'.');
						i++;
					}
				else if(head1 == "１"){
					tips = tips.replace(/１/,"1");
					tips = tips.replace(/２/,"2");
					tips = tips.replace(/３/,"3");
					tips = tips.replace(/４/,"4");
					tips = tips.replace(/５/,"5");
					tips = tips.replace(/６/,"6");
				}
				
				var head2 = tips.substring(0,2);
				switch(head2){
					case '1、':
						tips = tips.replace(/(\d)、/g, "$1.");
						break;
					case '1.':
						break;
					case '[1':
						tips = tips.replace(/\[(\d)\]/g,"$1.");
						break;	
					case '1）':
						tips = tips.replace(/(\d)）/g,"$1.");
						break;
					case '(1':
						tips = tips.replace(/\((\d)\)/g,"$1.");
						break;
				}
				var tipsList = tips.replace(/(\d+\.[^\d])/g,"</p><p>$1"); //1.0mm and 1.blabla is different
			    return tipsList;
			}

		function formatDisaster(tips){
			    var head3 = tips.substring(0,3);
				switch(head3){
					case "[为害": //[为害症状]
					case "[发生": //[发生规律]
						tips = tips.substring(6);					
						break;
					case "[形态":  //[形态"]
					case "[习性":  //[习性"]
					case "[侵染":  //[习性"]
						tips = tips.substring(4);
						break;
					default:
						return tips;
				}
				return formatTips(tips);
			}

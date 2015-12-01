<!doctype html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core" 
%>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="initial-scale=1.0">
  <title>disaster</title>
  <link href="http://fonts.googleapis.com/css?family=Roboto:400,400,700" rel="stylesheet" type="text/css">
  <link rel="stylesheet" href="jsp/css/standardize.css">
  <link rel="stylesheet" href="jsp/css/disaster-grid.css">
  <link rel="stylesheet" href="jsp/css/styles.css">
  <script type="text/javascript" src="jsp/js/format-baike.js"></script>
</head>
<body class="body page-disaster clearfix">
  <div class="disname clearfix">
    <p class="disname">${DisasterName}</p>
  </div>
  <div class="alititle alititle-1 clearfix">
    <p class="alititle">别名</p>
  </div>
  <div class="alias alias-1 clearfix">
    <p class="alias">${Alias}</p>
  </div>
  <div class="disimg clearfix" id="imageContainer">
  	<img class="image" src="../pic/${Images[0]}" />
  	<c:if test="${ImagesCount gt 1}">
		<script type="text/javascript">
			var imageContainer = document.getElementById('imageContainer');
			var images = new Array();
			<c:forEach items="${Images}" var="image"> 
				images.push("../pic/${image}");
			</c:forEach>
			if (typeof androidObj != 'undefined') {
				imageContainer.onclick = function() {
					androidObj.showAllImages(images);
				};
			}
      	</script>
	    <div class="countTag">${ImagesCount}张</div>
	</c:if>
  </div>
  <div class="symtitle symtitle-1 clearfix">
    <p class="symtitle">为害症状<br></p>
  </div>
  <div class="symptom symptom-1 clearfix">
    <div class="symptom">

	  <script type="text/javascript">
		var symptom = "${Symptom}";
		//var symptom = "[为害症状]刚孵化的幼虫常常群集在幼苗上的心叶或叶背上取，把叶片咬成小缺刻或网孔状。幼虫3龄后把蔬菜幼苗近地面的茎部咬断，还常将咬断的幼苗拖入洞中，其上部叶片往往露在穴外，使整株死亡，造成缺苗断垄。";
		document.write("<p id='dummys'> " + formatDisaster(symptom) );
		document.write("</p>");
		var obj = document.getElementById('dummys');
		if (obj.innerText == '') obj.parentNode.removeChild(obj);
		</script>	
	  
</div>	
  </div>
  <div class="phytitle phytitle-1 clearfix">
    <p class="phytitle">形态<br></p>
  </div>
  <div class="physical physical-1 clearfix">
    <div class="physical">
    	<script type="text/javascript">
		var morphology =  "${Morphology}";
		//var morphology = "[形态](1)有翅蚜：体长约1.6mm，长椭圆形。头胸部黑色，腹部黄绿色，薄被蜡粉。触角第3～5节依次有圆形次生感觉圈：21～29，7～14，0～4个。第1～6腹节各有独立缘斑，腹管前后斑愈合，第1节有背中窄横带，第5节上有小型中斑，第6～8节各有横带，第6节横带不规则。腹管淡黑，圆筒形，尾片圆锥形，两侧各有长毛2～3根。(2)无翅蚜：体长约2.0mm，长椭圆形，绿色或黑绿色，被薄粉。表皮粗糙，有菱形网纹。腹管长筒形，淡黑色，顶端收缩。";
		document.write("<p id='dummyf'> " + formatDisaster(morphology) );
		document.write("</p>");
		var obj = document.getElementById('dummyf');
		if (obj.innerText == '') obj.parentNode.removeChild(obj);
		</script>	
</div>
  </div>
  <div class="destitle destitle-1 clearfix">
    <p class="destitle">习性<br></p>
  </div>
  <div class="desciption desciption-1 clearfix">
    <div class="desciption">
        <script type="text/javascript">
		var habit =  "${Habit}"
		//var habit = "[习性](1)成虫。成虫具趋光性。雌蛾羽化后，由于无翅不能飞翔，只能沿树干爬上树。雄蛾羽化后飞到树上，白天多静止潜伏，天黑时雄蛾飞翔活跃，寻找雌蛾交尾，交尾后，当天即可产卵。(2)幼虫。幼虫孵化时，先将卵壳咬破，出壳后即可爬行吐丝。初孵幼虫食量小，随着虫龄的增长，食量猛增，暴食为害枣叶、嫩芽，甚至枣吊和枣花。";
		document.write("<p id='dummyh'> " + formatDisaster(habit) );
		document.write("</p>");
		var obj = document.getElementById('dummyh');
		if (obj.innerText == '') obj.parentNode.removeChild(obj);
		</script>	
</div>
  </div>
  <div class="haptitle clearfix">
    <div class="pt">发生规律<br></div>
  </div>
  <div class="happen happen-1 clearfix">
    <div class="happen">
      <script type="text/javascript">
		var pattern =  "${Pattern}";
		//var pattern = "[发生规律](1)世代。东北及华北多每年发生1代。河南、陕西、四川2代。(2)越冬。以前蛹在枝干上的茧内越冬。(3)时期。1代区5月中、下旬开始化蛹，蛹期15天左右。6月中旬至7月中旬出现成虫，成虫昼伏夜出，有趋光性，羽化后不久交配产卵，卵产于叶背，卵期7～10天，幼虫发生期6月下旬至8月，8月中旬后陆续老熟，在枝干等处结茧越冬。2代区5月上旬开始化蛹，5月下旬至6月上旬羽化，第1代幼虫6月中旬至7月上中旬发生，第1代成虫7月中下旬始见，第2代幼虫为害盛期在8月上中旬、8月下旬开始老熟结茧越冬。7～8月份高温干旱，黄刺蛾发生严重，田四周越冬的寄主树木多，利于刺蛾发生。"
		document.write("<p> " + formatDisaster(pattern) );
		document.write("</p>");
		
		/*
		[为害症状]刚孵化的幼虫常常群集在幼苗上的心叶或叶背上取，把叶片咬成小缺刻或网孔状。幼虫3龄后把蔬菜幼苗近地面的茎部咬断，还常将咬断的幼苗拖入洞中，其上部叶片往往露在穴外，使整株死亡，造成缺苗断垄。
		[形态]1.成虫。体长16～23mm，翅展42～54mm，体暗褐色。前翅内、外横线均为双线黑色，呈波浪形，前翅中室附近有一个肾形斑和一个环形斑。后翅灰白色，腹部灰色。老熟幼虫体长42～47mm，头黄褐色，体灰黑色。体背粗糙，布满龟裂状皱纹和黑色微小颗粒。2.幼虫。幼虫共分6龄。老熟幼虫体长42～47mm，头黄褐色，体灰黑色。体背粗糙，布满龟裂状皱纹和黑色微小颗粒。3.蛹。长18～23mm。赤褐色，有光泽，第5～7腹节背面的刻比侧面的刻点大，臀棘为短刺1对。
		[形态](1)有翅蚜：体长约1.6mm，长椭圆形。头胸部黑色，腹部黄绿色，薄被蜡粉。触角第3～5节依次有圆形次生感觉圈：21～29，7～14，0～4个。第1～6腹节各有独立缘斑，腹管前后斑愈合，第1节有背中窄横带，第5节上有小型中斑，第6～8节各有横带，第6节横带不规则。腹管淡黑，圆筒形，尾片圆锥形，两侧各有长毛2～3根。(2)无翅蚜：体长约2.0mm，长椭圆形，绿色或黑绿色，被薄粉。表皮粗糙，有菱形网纹。腹管长筒形，淡黑色，顶端收缩。
		[习性]1.成虫。成虫有假死性和趋光性，并对未腐熟的厩肥有强烈趋性，昼间藏在土壤中，晚8～9时为取食、交配活动盛期。产于松软湿润的土壤内，以水浇地最多。2.幼虫。蛴螬始终在地下活动，与土壤温湿度关系密切，当10cm土温达5℃时开始上升至表土层，13～18℃时活动最盛，23℃以上则往深土中移动。
		[习性](1)成虫。成虫具趋光性。雌蛾羽化后，由于无翅不能飞翔，只能沿树干爬上树。雄蛾羽化后飞到树上，白天多静止潜伏，天黑时雄蛾飞翔活跃，寻找雌蛾交尾，交尾后，当天即可产卵。(2)幼虫。幼虫孵化时，先将卵壳咬破，出壳后即可爬行吐丝。初孵幼虫食量小，随着虫龄的增长，食量猛增，暴食为害枣叶、嫩芽，甚至枣吊和枣花。
		[发生规律](1)世代。每年发生1代。(2)越冬。以蛹在树下土中8～10cm处越冬。(3)时期。3月下旬至4月上旬羽化。4月中下旬枣树发芽，幼虫孵化为害，为害盛期在5月份。5月下旬至6月上旬幼虫先后老熟，人土化蛹越夏越冬。
		[发生规律](1)世代。东北及华北多每年发生1代。河南、陕西、四川2代。(2)越冬。以前蛹在枝干上的茧内越冬。(3)时期。1代区5月中、下旬开始化蛹，蛹期15天左右。6月中旬至7月中旬出现成虫，成虫昼伏夜出，有趋光性，羽化后不久交配产卵，卵产于叶背，卵期7～10天，幼虫发生期6月下旬至8月，8月中旬后陆续老熟，在枝干等处结茧越冬。2代区5月上旬开始化蛹，5月下旬至6月上旬羽化，第1代幼虫6月中旬至7月上中旬发生，第1代成虫7月中下旬始见，第2代幼虫为害盛期在8月上中旬、8月下旬开始老熟结茧越冬。7～8月份高温干旱，黄刺蛾发生严重，田四周越冬的寄主树木多，利于刺蛾发生。
		[发生规律]1.发生世代　每年发生1代。2.越冬　以老熟幼虫在树干根颈部附近土内7～9cm深处结茧越冬。3.发生时期　6月上旬开始化蛹。6月下旬开始羽化为成虫。7月上旬幼虫开始危害，危害严重期在7月下旬至8月中旬，自8月下旬开始，幼虫逐渐老熟，下树入土结茧越冬。
		*/
		</script>
      
</div>
  </div>
</body>
</html>

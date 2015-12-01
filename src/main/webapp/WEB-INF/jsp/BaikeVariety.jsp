<!doctype html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
  <title>variety</title>
  <link rel="stylesheet" href="jsp/css/bootstrap.css">
  <script type="text/javascript" src="jsp/js/format-baike.js"></script>
</head>
<body class="baike">
<div class="container">
    <div class="row">
      <div class="col-md-12">
          <h1 class="text-left">
            ${Name} ${SubId}
          </h1>
          <div>
            <h3 class="baiketitle">审  定  号</h3>
            <p class="baikecontent">${ExaminationNumber}</p>
          </div>
          <div>
          <h3 class="baiketitle">育种单位</h3>
          <p class="baikecontent">${BreedingUnit}</p>
          </div>
          <div>
          <h3 class="baiketitle">适宜区域</h3>
          <p class="baikecontent">${SuitableRegion}</p>
          </div>
          <div>
          <h3 class="baiketitle">产量表现</h3>
          <p class="baikecontent">${Yield}</p>
          </div>
          <div>
            <h3 class="baiketitle">品种特性</h3>
        <script type="text/javascript">
		var chacts =  "${VarietyChacts}"//Characteristics
		//var chacts = "[习性](1)成虫。成虫具趋光性。雌蛾羽化后，由于无翅不能飞翔，只能沿树干爬上树。雄蛾羽化后飞到树上，白天多静止潜伏，天黑时雄蛾飞翔活跃，寻找雌蛾交尾，交尾后，当天即可产卵。(2)幼虫。幼虫孵化时，先将卵壳咬破，出壳后即可爬行吐丝。初孵幼虫食量小，随着虫龄的增长，食量猛增，暴食为害枣叶、嫩芽，甚至枣吊和枣花。";
		document.write("<p> " + formatDisaster(chacts));
		document.write("</p>");
		</script>	
        </div>
    </div>
</div>

</body>

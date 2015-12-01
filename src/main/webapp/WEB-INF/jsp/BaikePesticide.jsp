<!doctype html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
  <title>baikecpproduct</title>
  <link rel="stylesheet" href="jsp/css/bootstrap.css">
  <script type="text/javascript" src="jsp/js/format-baike.js"></script>
</head>
<body class="baike">
<div class="container">
    <div class="row">
      <div class="col-md-12">
          <h1 class="text-left">
            ${name}
          </h1>
          <div>
            <h3 class="baiketitle">有效成分</h3>
            <p class="baikecontent">${activeIngredient}</p>
          </div>
          <div>
          <h3 class="baiketitle">剂型</h3>
          <p class="baikecontent">${type}</p>
          </div>
          <div>
          <h3 class="baiketitle">生产厂家</h3>
          <p class="baikecontent">${manufacturer}</p>
          </div>
          <div>
          <h3 class="baiketitle">登记号</h3>
          <p class="baikecontent">${registrationId}</p>
          </div>
          <div>
            <h3 class="baiketitle">农药方法</h3>
          </div>
        </div>
        <div id="no-more-tables">

            <table class="col-md-12 table-bordered table-striped table-condensed cf">
        		<thead class="cf">
        			<tr>
        				<th>适用作物</th>
        				<th>防治对象</th>
        				<th>用药量</th>
        				<th>施用方法</th>
        			</tr>
        		</thead>
        		<tbody>
				
				<script type="text/javascript">
					var crops =  "${crop}";	
					var diseases = "${disease}";
					var volumes = "${volumn}";
					var methods = "${method}";
					
					var croplist = crops.split(/\s+/);
					var diseaselist = diseases.split(/\s+/);
					var volumelist = volumes.split(/\s+/);
					var methodlist = methods.split(/\s+/);
					length = croplist.length;
					if (length > diseaselist.length)
						length = diseaselist.length;
					if (length > volumelist.length)
						length = volumelist.length;
					if (length > methodlist.length)
						length = methodlist.length;
					for(var i=0; i<length; i++){
					document.write("<tr> <td data-title=\"适用作物\">"+croplist[i]+"</td> <td data-title=\"防治对象\">"+diseaselist[i]+"</td> <td data-title=\"用药量\">"+volumelist[i]+"</td> <td data-title=\"施用方法\">"+methodlist[i]+"</td> </tr> ");
				   }
				</script>
        		
				
				
        		</tbody>
        	</table>
        </div>
          <div class="col-md-12">
            <h3 class="baiketitle">用药指导</h3>
				
				<script type="text/javascript">
				var guidelines =  "${guideline}";
				document.write("<p> " + formatTips(guidelines) );
				document.write("</p>");
				</script>	
				
            <h3 class="baiketitle">注意事项</h3>
            	
				<script type="text/javascript">
					var tips =  "${tips}";	
					document.write("<p> " + formatTips(tips) );
					document.write("</p>");
				</script>
				
        </div>
    </div>
</div>

</body>

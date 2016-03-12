<!DOCTYPE HTML>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type"/>
    <meta content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="address=no" name="format-detection"/>
    <meta name="apple-mobile-web-app-capable" content="no" />
    <title>用户积分页</title>
    <link rel="stylesheet" href="jsp/css/darenjifen.css">
<body>
    <div class="banner">
        <img class="touxiang" src="#">
    </div>
    <div class="detail">
        <p class="title">当前积分</p>
        <!-- <img src="./img/test.png" style="display:block;margin:0 auto;"> -->
        
        <div class="circle-container">
            <div class="circle" id="circles-1"></div>
            <div class="circle-content">
                <div class="circle-text"><span class="curr-number"></span><span class="curr-buttom"></span></div>
                <div class="circle-prize"></div>
            </div>
        </div>

        <a href="html/detail.html">如何获得更多积分？</a>
    </div>

    <script src="jsp/js/zepto.min.js"></script> 
    <script src="jsp/js/circles.js"></script> 
    <script>
        $(function () {
            function getPointLevelObj (number) {
                var level = null;       // 等级
                var levelNumber = null; // 等级对应的最高积分
                var percentage = null;  // 进度条
                if (number > -1 && number <= 10) {
                    level = 1;
                    levelNumber = 10;
                    percentage = (number/10) * 100;
                } else if (number > 10 && number <= 20) {
                    level = 2;
                    levelNumber = 20;
                    percentage = ((number-10)/10) * 100;
                } else if (number > 20 && number <= 40) {
                    level = 3;
                    levelNumber = 40;
                    percentage = ((number-20)/20) * 100;
                } else if (number > 40 && number <= 60) {
                    level = 4;
                    levelNumber = 60;
                    percentage = ((number-40)/20) * 100;
                } else if (number > 60) {
                    level = 5;
                    percentage = 100;
                }
                return {
                    level: level,
                    levelNumber: levelNumber,
                    percentage: percentage
                }
            }


            var avatarUrl = "/avater/"+"${Avatar}";
            var memberPoints = ${MemberPoints};
            var levelObj = getPointLevelObj(memberPoints);
            
            $('.touxiang').attr('src', avatarUrl+'xxx');
            $('.curr-number').text(memberPoints);
            if (levelObj.level < 5) {
                $('.curr-buttom').text('/' + levelObj.levelNumber);
            }
            for (var i = 0; i < levelObj.level; i++) {
                $('.circle-prize').append('<i></i>');
            }

            $('.touxiang').on('error', function () {
                $('.touxiang').attr('src', '/avater/default.jpg');
                $('.touxiang').off('error');
            });

            // 初始化用户积分的图
            var color = ['#FCE6A4', '#fdc727'],
                child = document.getElementById('circles-1'),
                percentage = levelObj.percentage;

            Circles.create({
                id:         child.id,
                percentage: percentage,
                radius:     110,
                width:      22,
                number:     percentage / 10,
                //text:       '%',
                colors:     color
            });
        });
            
    </script>

</body>
</html>
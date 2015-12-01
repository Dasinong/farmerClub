package com.dasinong.farmerClub.weather;

import java.io.IOException;
import java.util.HashMap;

public class WeatherPhenomena {
	/*
	 * 1 晴 clear sunnyday 2 晴 clearnight sunnynight 3 阴 cloudy cloudy 4 阴转重度冻雨
	 * cloudyheavyfreezingrain frozenrainheavy 5 重度冻雨伴雷电
	 * cloudyheavyfreezingrainlightning icebigthunder 6 重度冻雨伴雷电
	 * cloudyheavyfreezingrainlightningnight icebigthunder 7 阴转重度冻雨
	 * cloudyheavyfreezingrainnight frozenrainheavy 8 阴转重度雨夹雪 cloudyheavymix
	 * rainbigsnow 9 阴转重度雨夹雪 cloudyheavymixlightning rainbigsnow 10 大雨夹雪伴雷电
	 * cloudyheavymixlightningnight rainbigsnowthunder 11 阴转重度雨夹雪
	 * cloudyheavymixnight rainbigsnow 12 阴转暴雨 cloudyheavyrain rainheavy 13
	 * 暴雨并伴有雷电 cloudyheavyrainlightning rainheavierthunder 14 暴雨并伴有雷电
	 * cloudyheavyrainlightningnight rainheavierthunder 15 阴转暴雨
	 * cloudyheavyrainnight rainheavy 16 重度的冰粒 cloudyheavysleet icebig 17 重度的冰粒
	 * cloudyheavysleetlightning icebig 18 重度冰粒伴雷电
	 * cloudyheavysleetlightningnight icebigthunder 19 重度的冰粒
	 * cloudyheavysleetnight icebig 20 阴转大雪 cloudyheavysnow snowbig 21 大雪并伴有雷电
	 * cloudyheavysnowlightning snowbigheavythunder 22 大雪并伴有雷电
	 * cloudyheavysnowlightningnight snowbigheavythunder 23 大雪
	 * cloudyheavysnownight snowbig 24 轻度的冻雨 cloudylightfreezingrain
	 * sunnywithfrozenrainday 25 轻度的冻雨 cloudylightfreezingrainnight
	 * sunnywithfrozenrainnight 26 轻度的雨夹雪 cloudylightmix sunnyrainsnowday 27
	 * 轻度的雨夹雪 cloudylightmixnight sunnyrainsnownight 28 小雨 cloudylightrain
	 * rainsm 29 小雨 cloudylightrainlightning rainsm 30 小雨并伴有雷电
	 * cloudylightrainlightningnight rainsmthunder 31 小雨 cloudylightrainnight
	 * rainsm 32 轻度的冰粒 cloudylightsleet sunnywithiceday 33 轻度的冰粒
	 * cloudylightsleetnight sunnywithicenight 34 小雪 cloudylightsnow snowsm 35
	 * 小雪 cloudylightsnownight snowsm 36 中度的冻雨 cloudymediumfreezingrain
	 * frozenrain 37 冻雨伴雷电 cloudymediumfreezingrainlightning icemidthunder 38
	 * 冻雨伴雷电 cloudymediumfreezingrainlightningnight icemidthunder 39 中度的冻雨
	 * cloudymediumfreezingrainnight frozenrain 40 中度的雨夹雪 cloudymediummix
	 * rainmidsnow 41 晴转中度雨夹雪 cloudymediummixlightning rainmidsnowthunder 42
	 * 雨夹雪伴雷电 cloudymediummixlightningnight rainmidsnowthunder 43 中度的雨夹雪
	 * cloudymediummixnight rainmidsnow 44 中雨 cloudymediumrain rainmid 45
	 * 中雨并伴有雷电 cloudymediumrainlightning rainmidthunder 46 中雨并伴有雷电
	 * cloudymediumrainlightningnight rainmidthunder 47 中雨 cloudymediumrainnight
	 * rainmid 48 中度的冰粒 cloudymediumsleet sunnywithiceday 49 冰粒伴雷电
	 * cloudymediumsleetlightning icemidthunder 50 冰粒伴雷电
	 * cloudymediumsleetlightningnight icemidthunder 51 中度的冰粒
	 * cloudymediumsleetnight sunnywithicenight 52 中雪 cloudymediumsnow snowmid
	 * 53 中雪并伴有雷电 cloudymediumsnowlightning snowmidthunder 54 中雪并伴有雷电
	 * cloudymediumsnowlightningnight snowmidthunder 55 中雪 cloudymediumsnownight
	 * snowmid 56 阴 cloudynight cloudy 57 零星冻雨 cloudyverylightfreezingrain
	 * sunnywithfrozenrainday 58 零星冻雨 cloudyverylightfreezingrainnight
	 * sunnywithfrozenrainnight 59 零星雨夹雪 cloudyverylightmix sunnyrainsnowday 60
	 * 零星雨夹雪 cloudyverylightmixnight sunnyrainsnownight 61 毛毛雨
	 * cloudyverylightrain sunnyrainday 62 毛毛雨 cloudyverylightrainnight
	 * sunnyrainnight 63 零星冰粒 cloudyverylightsleet sunnywithiceday 64 轻微的冰粒
	 * cloudyverylightsleetnight sunnywithicenight 65 零星小雪 cloudyverylightsnow
	 * sunnysnowday 66 零星小雪 cloudyverylightsnownight sunnysnownight 67 晴天有云
	 * mostlyclear cloudyday 68 晴转重度冻雨 mostlyclearheavyfreezingrain
	 * frozenrainheavy 69 重度冻雨伴雷电 mostlyclearheavyfreezingrainlightning
	 * icebigthunder 70 重度冻雨伴雷电 mostlyclearheavyfreezingrainlightningnight
	 * icebigthunder 71 重度的雨夹雪 mostlyclearheavymix rainbigsnow 72 大雨夹雪伴雷电
	 * mostlyclearheavymixlightning rainbigsnowthunder 73 大雨夹雪伴雷电
	 * mostlyclearheavymixlightningnight rainbigsnowthunder 74 晴转暴雨
	 * mostlyclearheavymixnight rainheavy 75 暴雨并伴有雷电 mostlyclearheavyrain
	 * rainheavierthunder 76 暴雨并伴有雷电 mostlyclearheavyrainlightning
	 * rainheavierthunder 77 暴雨并伴有雷电 mostlyclearheavyrainlightningnight
	 * rainheavierthunder 78 晴转暴雨 mostlyclearheavyrainnight rainheavy 79 晴转大冰粒
	 * mostlyclearheavysleet icebig 80 重度冰粒伴雷电 mostlyclearheavysleetlightning
	 * icebigthunder 81 重度冰粒伴雷电 mostlyclearheavysleetlightningnight
	 * icebigthunder 82 晴转大冰粒 mostlyclearheavysleetnight icebig 83 晴转暴雪
	 * mostlyclearheavysnow snowbigheavy 84 暴雪并伴有雷电
	 * mostlyclearheavysnowlightning snowbigheavythunder 85 暴雪并伴有雷电
	 * mostlyclearheavysnowlightningnight snowbigheavythunder 86 晴转暴雪
	 * mostlyclearheavysnownight snowbigheavy 87 晴伴有轻微冻雨
	 * mostlyclearlightfreezingrain sunnywithfrozenrainday 88 轻微冻雨伴雷电
	 * mostlyclearlightfreezingrainnight sunnywithfrozenrainnight 89 轻微雨夹雪
	 * mostlyclearlightmix sunnyrainsnowday 90 轻微雨夹雪 mostlyclearlightmixnight
	 * sunnyrainsnownight 91 晴转小雨 mostlyclearlightrain rainsm 92 晴转小雨伴雷电
	 * mostlyclearlightrainlightning rainsmthunder 93 晴转小雨伴雷电
	 * mostlyclearlightrainlightningnight rainsmthunder 94 晴转小雨
	 * mostlyclearlightrainnight rainsm 95 晴伴有轻微冰粒 mostlyclearlightsleet
	 * sunnywithiceday 96 晴伴有轻微冰粒 mostlyclearlightsleetnight sunnywithicenight
	 * 97 晴转小雪 mostlyclearlightsnow snowsm 98 晴转小雪 mostlyclearlightsnownight
	 * snowsm 99 晴转冻雨 mostlyclearmediumfreezingrain frozenrain 100 晴转冻雨伴雷电
	 * mostlyclearmediumfreezingrainlightning icemidthunder 101 晴转冻雨伴雷电
	 * mostlyclearmediumfreezingrainlightningnight icemidthunder 102 冻雨伴雷电
	 * mostlyclearmediumfreezingrainnight icemidthunder 103 晴转冻雨
	 * mostlyclearheavyfreezingrainnight frozenrain 104 晴转中度雨夹雪
	 * mostlyclearmediummix rainmidsnow 105 雨夹雪伴雷电 mostlyclearmediummixlightning
	 * rainmidsnowthunder 106 雨夹雪伴雷电 mostlyclearmediummixlightningnight
	 * rainmidsnowthunder 107 雨夹雪伴雷电 mostlyclearmediummixnight
	 * rainmidsnowthunder 108 晴转中雨 mostlyclearmediumrain rainmid 109 晴转中雨伴雷电
	 * mostlyclearmediumrainlightning rainmidthunder 110 晴转中雨伴雷电
	 * mostlyclearmediumrainlightningnight rainmidthunder 111 晴转中雨
	 * mostlyclearmediumrainnight rainmid 112 晴伴中等冰粒 mostlyclearmediumsleet
	 * sunnywithiceday 113 晴转冰粒伴雷电 mostlyclearmediumsleetlightning icemidthunder
	 * 114 晴转冰粒伴雷电 mostlyclearmediumsleetlightningnight icemidthunder 115 晴伴中等冰粒
	 * mostlyclearmediumsleetnight sunnywithicenight 116 晴转中雪
	 * mostlyclearmediumsnow snowmid 117 晴转中雪伴雷电 mostlyclearmediumsnowlightning
	 * snowmidthunder 118 晴转中雪伴雷电 mostlyclearmediumsnowlightningnight
	 * snowmidthunder 119 晴转中雪 mostlyclearmediumsnownight snowmid 120 晴天有云
	 * mostlyclearnight cloudynight 121 晴天偶有小冻雨 mostlyclearverylightfreezingrain
	 * sunnywithfrozenrainday 122 晴天偶有小冻雨 mostlyclearverylightfreezingrainnight
	 * sunnywithfrozenrainnight 123 晴天偶有雨夹雪 mostlyclearverylightmix
	 * sunnyrainsnowday 124 晴天偶有雨夹雪 mostlyclearverylightmixnight
	 * sunnyrainsnownight 125 晴天偶有微雨 mostlyclearverylightrain sunnyrainsnowday
	 * 126 晴天偶有微雨 mostlyclearverylightrainnight sunnyrainsnownight 127 晴天偶有小冰粒
	 * mostlyclearverylightsleet sunnywithiceday 128 晴天偶有小冰粒
	 * mostlyclearverylightsleetnight sunnywithicenight 129 晴天偶有微雪
	 * mostlyclearverylightsnow sunnyrainsnowday 130 晴天偶有微雪
	 * mostlyclearverylightsnownight sunnyrainsnownight 131 大部多云 mostlycloudy
	 * cloudyday 132 重度的冻雨 mostlycloudyheavyfreezingrain frozenrainheavy 133
	 * 重度冻雨伴雷电 mostlycloudyheavyfreezingrainlightning icebigthunder 134 重度冻雨伴雷电
	 * mostlycloudyheavyfreezingrainlightningnight icebigthunder 135 重度的冻雨
	 * mostlycloudyheavyfreezingrainnight frozenrainheavy 136 重度的雨夹雪
	 * mostlycloudyheavymix rainbigsnow 137 大雨夹雪伴雷电
	 * mostlycloudyheavymixlightning rainbigsnowthunder 138 大雨夹雪伴雷电
	 * mostlycloudyheavymixlightningnight rainbigsnowthunder 139 重度的雨夹雪
	 * mostlycloudyheavymixnight rainbigsnow 140 阴转暴雪 mostlycloudyheavyrain
	 * rainheavy 141 暴雨并伴有雷电 mostlycloudyheavyrainlightning rainheavierthunder
	 * 142 暴雨并伴有雷电 mostlycloudyheavyrainlightningnight rainheavierthunder 143
	 * 阴转暴雪 mostlycloudyheavyrainnight rainheavy 144 重度的冰粒
	 * mostlycloudyheavysleet icebig 145 重度冰粒伴雷电 mostlycloudyheavysleetlightning
	 * icebigthunder 146 重度冰粒伴雷电 mostlycloudyheavysleetlightningnight
	 * icebigthunder 147 重度的冰粒 mostlycloudyheavysleetnight icebig 148 阴转暴雪
	 * mostlycloudyheavysnow snowbigheavy 149 暴雪并伴有雷电
	 * mostlycloudyheavysnowlightning snowbigheavythunder 150 暴雪并伴有雷电
	 * mostlycloudyheavysnowlightningnight snowbigheavythunder 151 阴转暴雪
	 * mostlycloudyheavysnownight snowbigheavy 152 轻微冻雨
	 * mostlycloudylightfreezingrain sunnywithfrozenrainday 153 轻微冻雨
	 * mostlycloudylightfreezingrainnight sunnywithfrozenrainnight 154 轻微雨夹雪
	 * mostlycloudylightmix sunnyrainsnowday 155 轻微雨夹雪 mostlycloudylightmixnight
	 * sunnyrainsnownight 156 小雨 mostlycloudylightrain rainsm 157 小雨并伴有雷电
	 * mostlycloudylightrainlightning rainsmthunder 158 小雨并伴有雷电
	 * mostlycloudylightrainlightningnight rainsmthunder 159 小雨
	 * mostlycloudylightrainnight rainsm 160 轻微的冰粒 mostlycloudylightsleet
	 * sunnywithiceday 161 轻微的冰粒 mostlycloudylightsleetnight sunnywithicenight
	 * 162 小雪 mostlycloudylightsnow snowsm 163 小雪 mostlycloudylightsnownight
	 * snowsm 164 中等的冻雨 mostlycloudymediumfreezingrain frozenrain 165 冻雨伴雷电
	 * mostlycloudymediumfreezingrainlightning icemidthunder 166 冻雨伴雷电
	 * mostlycloudymediumfreezingrainlightningnight icemidthunder 167 中等的冻雨
	 * mostlycloudymediumfreezingrainnight frozenrain 168 中等的雨夹雪
	 * mostlycloudymediummix rainmidsnow 169 中雨夹雪伴雷电
	 * mostlycloudymediummixlightning rainmidsnowthunder 170 中雨夹雪伴雷电
	 * mostlycloudymediummixlightningnight rainmidsnowthunder 171 中等的雨夹雪
	 * mostlycloudymediummixnight rainmidsnow 172 中雨 mostlycloudymediumrain
	 * rainmid 173 中雨并伴有雷电 mostlycloudymediumrainlightning rainmidthunder 174
	 * 中雨并伴有雷电 mostlycloudymediumrainlightningnight rainmidthunder 175 中雨
	 * mostlycloudymediumrainnight rainmid 176 中等的冰粒 mostlycloudymediumsleet
	 * sunnywithiceday 177 冰粒伴雷电 mostlycloudymediumsleetlightning icemidthunder
	 * 178 冰粒伴雷电 mostlycloudymediumsleetlightningnight icemidthunder 179 中等的冰粒
	 * mostlycloudymediumsleetnight sunnywithicenight 180 中雪
	 * mostlycloudymediumsnow snowmid 181 中雪并伴有雷电
	 * mostlycloudymediumsnowlightning snowmidthunder 182 中雪并伴有雷电
	 * mostlycloudymediumsnowlightningnight snowmidthunder 183 中雪
	 * mostlycloudymediumsnownight snowmid 184 大部多云 mostlycloudynight
	 * cloudynight 185 阴偶有小冻雨 mostlycloudyverylightfreezingrain
	 * sunnywithfrozenrainday 186 阴偶有小冻雨 mostlycloudyverylightfreezingrainnight
	 * sunnywithfrozenrainnight 187 阴偶有雨夹雪 mostlycloudyverylightmix
	 * sunnyrainsnowday 188 阴偶有雨夹雪 mostlycloudyverylightmixnight
	 * sunnyrainsnownight 189 阴偶有小雨 mostlycloudyverylightrain sunnyrainday 190
	 * 阴偶有小雨 mostlycloudyverylightrainnight sunnyrainnight 191 阴偶有小冰粒
	 * mostlycloudyverylightsleet sunnywithiceday 192 多云偶有小冰粒
	 * mostlycloudyverylightsleetnight sunnywithicenight 193 阴偶有小雪
	 * mostlycloudyverylightsnow sunnysnowday 194 阴偶有小雪
	 * mostlycloudyverylightsnownight sunnysnownight 195 多云 partlycloudy
	 * cloudyday 196 重度的冻雨 partlycloudyheavyfreezingrain frozenrainheavy 197
	 * 重度冻雨伴雷电 partlycloudyheavyfreezingrainlightning icebigthunder 198 重度的冻雨
	 * partlycloudyheavyfreezingrainlightningnight frozenrainheavy 199 重度的冻雨
	 * partlycloudyheavyfreezingrainnight frozenrainheavy 200 重度的雨夹雪
	 * partlycloudyheavymix rainbigsnow 201 大雨夹雪伴雷电
	 * partlycloudyheavymixlightning rainbigsnowthunder 202 大雨夹雪伴雷电
	 * partlycloudyheavymixlightningnight rainbigsnowthunder 203 重度的雨夹雪
	 * partlycloudyheavymixnight rainbigsnow 204 多云转暴雨 partlycloudyheavyrain
	 * rainheavy 205 暴雨并伴有雷电 partlycloudyheavyrainlightning rainheavierthunder
	 * 206 暴雨并伴有雷电 partlycloudyheavyrainlightningnight rainheavierthunder 207
	 * 多云转暴雨 partlycloudyheavyrainnight rainheavy 208 重度的冰粒
	 * partlycloudyheavysleet icebig 209 重度冰粒伴雷电 partlycloudyheavysleetlightning
	 * icebigthunder 210 重度冰粒伴雷电 partlycloudyheavysleetlightningnight
	 * icebigthunder 211 重度的冰粒 partlycloudyheavysleetnight icebig 212 多云转暴雪
	 * partlycloudyheavysnow snowbigheavy 213 暴雪并伴有雷电
	 * partlycloudyheavysnowlightning snowbigheavythunder 214 暴雪并伴有雷电
	 * partlycloudyheavysnowlightningnight snowbigheavythunder 215 多云转暴雪
	 * partlycloudyheavysnownight snowbigheavy 216 轻微冻雨
	 * partlycloudylightfreezingrain sunnywithfrozenrainday 217 轻微冻雨
	 * partlycloudylightfreezingrainnight sunnywithfrozenrainnight 218 轻微雨夹雪
	 * partlycloudylightmix sunnyrainsnowday 219 轻微雨夹雪 partlycloudylightmixnight
	 * sunnyrainsnownight 220 小雨 partlycloudylightrain rainsm 221 小雨并伴有雷电
	 * partlycloudylightrainlightning rainsmthunder 222 小雨并伴有雷电
	 * partlycloudylightrainlightningnight rainsmthunder 223 小雨
	 * partlycloudylightrainnight rainsm 224 轻微的冰粒 partlycloudylightsleet
	 * sunnywithiceday 225 轻微的冰粒 partlycloudylightsleetnight sunnywithicenight
	 * 226 小雪 partlycloudylightsnow snowsm 227 小雪 partlycloudylightsnownight
	 * snowsm 228 中等的冻雨 partlycloudymediumfreezingrain frozenrain 229 冻雨伴雷电
	 * partlycloudymediumfreezingrainlightning icemidthunder 230 冻雨伴雷电
	 * partlycloudymediumfreezingrainlightningnight icemidthunder 231 冻雨伴雷电
	 * partlycloudymediumfreezingrainnight icemidthunder 232 中等的雨夹雪
	 * partlycloudymediummix rainmidsnow 233 中雨夹雪伴雷电
	 * partlycloudymediummixlightning rainmidsnowthunder 234 中雨夹雪伴雷电
	 * partlycloudymediummixnight rainmidsnowthunder 235 中等的雨夹雪
	 * partlycloudymediummixlightningnight rainmidsnow 236 中雨
	 * partlycloudymediumrain rainmid 237 中雨并伴有雷电
	 * partlycloudymediumrainlightning rainmidthunder 238 中雨并伴有雷电
	 * partlycloudymediumrainlightningnight rainmidthunder 239 中雨
	 * partlycloudymediumrainnight rainmid 240 中等的冰粒 partlycloudymediumsleet
	 * sunnywithiceday 241 冰粒伴雷电 partlycloudymediumsleetlightning icemidthunder
	 * 242 冰粒伴雷电 partlycloudymediumsleetlightningnight icemidthunder 243 中等的冰粒
	 * partlycloudymediumsleetnight sunnywithicenight 244 中雪
	 * partlycloudymediumsnow snowmid 245 中雪并伴有雷电
	 * partlycloudymediumsnowlightning snowmidthunder 246 中雪并伴有雷电
	 * partlycloudymediumsnowlightningnight snowmidthunder 247 中雪
	 * partlycloudymediumsnownight snowmid 248 多云 partlycloudynight cloudynight
	 * 249 多云偶有小冻雨 partlycloudyverylightfreezingrain sunnywithfrozenrainday 250
	 * 多云偶有小冻雨 partlycloudyverylightfreezingrainnight sunnywithfrozenrainnight
	 * 251 多云偶有雨夹雪 partlycloudyverylightmix sunnyrainsnowday 252 多云偶有雨夹雪
	 * partlycloudyverylightmixnight sunnyrainsnownight 253 多云偶有小雨
	 * partlycloudyverylightrain sunnyrainday 254 多云偶有小雨
	 * partlycloudyverylightrainnight sunnyrainnight 255 多云偶有小冰粒
	 * partlycloudyverylightsleet sunnywithiceday 256 多云偶有小冰粒
	 * partlycloudyverylightsleetnight sunnywithicenight 257 多云偶有小雪
	 * partlycloudyverylightsnow sunnysnowday 258 多云偶有小雪
	 * partlycloudyverylightsnownight sunnysnownight
	 */
	/*
	 * iconMaps.put("00", "sunnyday"); iconMaps.put("01", "cloudyday");
	 * iconMaps.put("02", "cloudy"); iconMaps.put("03", "rainscatteredday");
	 * iconMaps.put("04", "stormday"); iconMaps.put("05", "stormhail");
	 * iconMaps.put("06", "rainsnow"); iconMaps.put("07", "rainsmall");
	 * iconMaps.put("08", "rainmid"); iconMaps.put("09", "rainbig");
	 * iconMaps.put("10", "rainheavy"); iconMaps.put("11", "rainheavier");
	 * iconMaps.put("12", "rainsevereextreme"); iconMaps.put("13",
	 * "snowscatteredday"); iconMaps.put("14", "snowsmall"); iconMaps.put("15",
	 * "snowmid"); iconMaps.put("16", "snowbig"); iconMaps.put("17",
	 * "snowbigheavy"); iconMaps.put("18", "fogday"); iconMaps.put("19",
	 * "frozenrain"); iconMaps.put("20", "duststorm"); iconMaps.put("21",
	 * "rainsmallmid"); iconMaps.put("22", "rainmidbig"); iconMaps.put("23",
	 * "rainbigheavy"); iconMaps.put("24", "rainheavyheavier");
	 * iconMaps.put("25", "rainheaviersevere"); iconMaps.put("26",
	 * "snowsmallmid"); iconMaps.put("27", "snowmidbig"); iconMaps.put("28",
	 * "snowbigheavy"); iconMaps.put("29", "dust"); iconMaps.put("30",
	 * "dustmid"); iconMaps.put("31", "duststormheavy"); iconMaps.put("53",
	 * "haze"); iconMaps.put("99", "na");
	 */
	private static WeatherPhenomena WeatherPhenomena;

	public static WeatherPhenomena getWeatherPhenomena() {
		if (WeatherPhenomena == null) {
			WeatherPhenomena = new WeatherPhenomena();
			return WeatherPhenomena;
		} else {
			return WeatherPhenomena;
		}
	}

	private WeatherPhenomena() {
		iconMaps = new HashMap<Integer, String>();
		loadContent();
	}

	private void loadContent() {
		iconMaps.put(0, "sunnyday");
		iconMaps.put(1, "cloudyday");
		iconMaps.put(2, "cloudy");
		iconMaps.put(3, "rainscatteredday");
		iconMaps.put(4, "stormday");
		iconMaps.put(5, "stormhail");
		iconMaps.put(6, "rainsnow");
		iconMaps.put(7, "rainsmall");
		iconMaps.put(8, "rainmid");
		iconMaps.put(9, "rainbig");
		iconMaps.put(10, "rainheavy");
		iconMaps.put(11, "rainheavier");
		iconMaps.put(12, "rainsevereextreme");
		iconMaps.put(13, "snowscatteredday");
		iconMaps.put(14, "snowsmall");
		iconMaps.put(15, "snowmid");
		iconMaps.put(16, "snowbig");
		iconMaps.put(17, "snowbigheavy");
		iconMaps.put(18, "fogday");
		iconMaps.put(19, "frozenrain");
		iconMaps.put(20, "duststorm");
		iconMaps.put(21, "rainsmallmid");
		iconMaps.put(22, "rainmidbig");
		iconMaps.put(23, "rainbigheavy");
		iconMaps.put(24, "rainheavyheavier");
		iconMaps.put(25, "rainheaviersevere");
		iconMaps.put(26, "snowsmallmid");
		iconMaps.put(27, "snowmidbig");
		iconMaps.put(28, "snowbigheavy");
		iconMaps.put(29, "dust");
		iconMaps.put(30, "dustmid");
		iconMaps.put(31, "duststormheavy");
		iconMaps.put(53, "haze");
		iconMaps.put(99, "na");
	}

	public String getIcon(Integer id) {
		return iconMaps.get(id);
	}

	public String getIcon(Short id) {
		return iconMaps.get(id.intValue());
	}

	public static HashMap<Integer, String> iconMaps;

	public static void main(String[] args) {
		short id = 1;
		System.out.println(WeatherPhenomena.getWeatherPhenomena().getIcon(id));
	}

}

$(document).ready(function(){

	var eventData = {};
	var today = new Date()
	var startDate = new Date(today.getTime() - (7 * 24 * 60 * 60 * 1000)).toISOString().slice(0, 11) + '00:00:00.000Z';
	
	// 일주일 간 발생한 이벤트 데이터 조회
	console.log(startDate);
	$.get('http://125.131.73.85:8080/epcis/Service/Poll/SimpleEventQuery?GE_eventTime=' + startDate)
	.done(function(doc) {
		eventData = parseEventQueryData(doc);
		
//		console.log(eventData.open);
		
		showEventChart(eventData);
		
	}).fail(function(data) {
		console.log("Error: " + data);
	})

	// 최근 10개 이벤트 데이터 조회
	var EVENT_COUNT_LIMIT = 10;
	var eventList 
	$.ajax({
		type: "GET",
		url: "http://125.131.73.85:8080/epcis/Service/Poll/SimpleEventQuery?orderBy=eventTime&eventCountLimit=" + EVENT_COUNT_LIMIT,
		dataType: "xml",
		success: function(xml) {
//			console.log(xml);
			var eventList = parseLatestEventQueryData(xml);
			for(var i=0; eventList.length; i++) {
				$("#latestEventTable > tbody:last").append('<tr><td>'+eventList[i][0]+'</td><td>'+eventList[i][1]+'</td><td>'+eventList[i][2]+'</td></tr>')
			}
			
		},
		error: function(xhr, status, error) {
			console.log(error);
		}
	});

})

function parseLatestEventQueryData(doc) {
	// eventTime(day, time), bizStep
	var eventList = doc.getElementsByTagName('EventList')[0].childNodes;
	var data = [];
	
	for(var i=0; i<eventList.length; i++) {
		
		// 텍스트 노드 제거
		if(eventList[i].nodeName != '#text') {
			eventTime = eventList[i].getElementsByTagName('eventTime')[0].firstChild.nodeValue;
			eventTimeZoneOffset = eventList[i].getElementsByTagName('eventTimeZoneOffset')[0].firstChild.nodeValue.split(':')[0];
			bizStepList = eventList[i].getElementsByTagName('bizStep')[0].firstChild.nodeValue.split(':');
			event = bizStepList[bizStepList.length-1].toUpperCase();
			
			time = new Date(eventTime);
			time = new Date(time.getTime() + (parseInt(eventTimeZoneOffset) * 60 * 60 * 1000));	// TimeZoneOffset 보정 
			
			textDate = time.getFullYear() +'-'+ ('0' + (time.getMonth() + 1)).slice(-2) +'-'+ ('0' + time.getDate()).slice(-2)
			textTime = ('0' + time.getHours()).slice(-2) + ':' + ('0' + time.getMinutes()).slice(-2) +':' + ('0' + time.getSeconds()).slice(-2);
			
			data.push([textDate,textTime,event])
			
		}
	} 
	
	return data;
	
}

function parseEventQueryData(doc) {
	// eventTime(day, time), bizStep
	var eventList = doc.getElementsByTagName('EventList')[0].childNodes;
	var data={open:[], close:[], push:[], pull:[]};
	var eventTime;
	var bizStep;
	
	for(var i=0; i<eventList.length; i++) {
		
		// 텍스트 노드 제거
		if(eventList[i].nodeName != '#text') {
//			console.log(eventList[i]);
			eventTime = eventList[i].getElementsByTagName('eventTime')[0].firstChild.nodeValue;
			eventTimeZoneOffset = eventList[i].getElementsByTagName('eventTimeZoneOffset')[0].firstChild.nodeValue.split(':')[0];
			bizStepList = eventList[i].getElementsByTagName('bizStep')[0].firstChild.nodeValue.split(':');
			event = bizStepList[bizStepList.length-1];
			
			time = new Date(eventTime);
			time = new Date(time.getTime() + (parseInt(eventTimeZoneOffset) * 60 * 60 * 1000));	// TimeZoneOffset 보정 
			
			textDate = time.getFullYear() +'-'+ ('0' + (time.getMonth() + 1)).slice(-2) +'-'+ ('0' + time.getDate()).slice(-2)
//			textTime = ('0' + time.getHours()).slice(-2) + ':' + ('0' + time.getMinutes()).slice(-2) +':' + ('0' + time.getSeconds()).slice(-2);
			
			console.log(textDate);
			
			// 날짜 및 시간이 고정된 데이터 생성
			valTime = new Date('2018','12','1',time.getHours(),time.getMinutes(),time.getSeconds() );
			valDate = new Date(time.getFullYear(),time.getMonth(),time.getDate());
			
			if('open' === event) {
				data.open.push([valDate.getTime(), valTime.getTime()])
			} else if('close' === event) {
				data.close.push([valDate.getTime(), valTime.getTime()])
			} else if('push' === event) {
				data.push.push([valDate.getTime(), valTime.getTime()])
			} else if('pull' === event) {
				data.pull.push([valDate.getTime(), valTime.getTime()])
			}
			
		}
	} 
	
	return data;
	
}

function showEventChart(eventData) {
	Highcharts.chart('container', {
	    chart: {
	        type: 'scatter',
	        zoomType: 'xy'
	    },
	    credits: {
            enabled: false
        },
	    title: {
	        text: 'All events of the Fridge for the last week'
	    },
	    tooltip: {
        	formatter: function() {
                return  '<b>' + this.series.name +'</b><br/>' 
                + Highcharts.dateFormat('%Y-%b-%e', new Date(this.x + 9*60*60*1000)) 
                	 + ' ' 
                + Highcharts.dateFormat('%H:%M:%S', new Date(this.y + 9*60*60*1000));
            }
        },
	    xAxis: {
	    	title: {
	            text: 'Day'
	        },
	        type: 'datetime',
	        dateTimeLabelFormats: { 
	        	day: '%e. %b'
	        },
	        labels: {
	            formatter: function() {
	            	return Highcharts.dateFormat('%e. %b', this.value);
	        	}
	        },
	        tickInterval: 24 * 60 * 60 * 1000
	    },
	    yAxis: {
	        title: {
	            text: 'Hour'
	        },
	        type: 'datetime',
	        dateTimeLabelFormats: { 
	        	hour: '%H:%M'
	        },
	        labels: {
	            formatter: function() {
	            	return Highcharts.dateFormat('%H:%M', this.value);
	        	}
	        }
	        
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'left',
	        verticalAlign: 'top',
	        x: 100,
	        y: 30,
	        floating: true,
	        backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
	        borderWidth: 1
	    },
	    plotOptions: {
	        scatter: {
	            marker: {
	                radius: 5,
	                states: {
	                    hover: {
	                        enabled: true,
	                        lineColor: 'rgb(100,100,100)'
	                    }
	                }
	            },
	            states: {
	                hover: {
	                    marker: {
	                        enabled: false
	                    }
	                }
	            },
	            tooltip: {
                    headerFormat: '<b>{series.name}</b><br>'
	            }
	        }
	    },
	    series: [{
	        name: 'Open',
	        color: 'rgba(223, 83, 83, .5)',
	        data: eventData.open,
	        marker: {
	        	fillColor: '#FFFFFF',
	        	lineWidth: 2,
                lineColor: 'rgba(0, 0, 0, .5)',
	        	symbol:'circle'
	        }
	        
	    }, {
	        name: 'Close',
	        color: 'rgba(0, 0, 0, .5)',
	        data: eventData.close,
	        marker: {
	        	symbol:'circle'
	        }
	    }, {
	        name: 'Push',
	        color: 'rgba(119, 152, 191, .5)',
	        data: eventData.push,
	        marker: {
	        	symbol:'square'
	        }
	    }, {
	        name: 'Pull',
	        color: 'rgba(223, 83, 83, .5)',
	        data: eventData.pull,
	        marker: {
	        	symbol:'diamond'
	        }
	    }]
	});
}

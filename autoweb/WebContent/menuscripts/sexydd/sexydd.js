$(document).ready(function(){  
    
      $("ul.sd-subnav").parent().append("<span></span>"); //Only shows drop down trigger when js is enabled (Adds empty span tag after ul.subnav*)  
    
      $("ul.sd-topnav li span").click(function() { //When trigger is clicked...  
    
          //Following events are applied to the subnav itself (moving subnav up and down)  
          $(this).parent().find("ul.sd-subnav").slideDown('fast').show(); //Drop down the subnav on click  
    
          $(this).parent().hover(function() {  
          }, function(){  
              $(this).parent().find("ul.sd-subnav").slideUp('fast'); //When the mouse hovers out of the subnav, move it back up  
          });  
    
          //Following events are applied to the trigger (Hover events for the trigger)  
          }).hover(function() {  
              $(this).addClass("sd-subhover"); //On hover over, add class "subhover"  
          }, function(){  //On Hover Out  
              $(this).removeClass("sd-subhover"); //On hover out, remove class "subhover"  
      });  
      
      
 });  
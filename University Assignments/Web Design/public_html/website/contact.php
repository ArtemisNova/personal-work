<!DOCTYPE html>

<html>
	
	<head>
		<meta charset="utf-8">
		<title>Crimson Fitness | About Us</title>
		<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
	</head>
	
	<body>
		
		<div id="wrapper">
			<div id="header">
				<h1><a href="index.html"><img src="images/logo_smallest.jpg" default="home page"></a></h1>
			</div>
			<!-- header end -->
			
			<div id="menu">
				<ul>
					<li><a onmouseover="document.home_button.src='images/home_button_rollover.jpg'" onmouseout="document.home_button.src='images/home_button.jpg'" href="index.html">
																											<img src="images/home_button.jpg" alt="Home" name="home_button"></a></li>
					<li><a onmouseover="document.about_button.src='images/about_button_rollover.jpg'" onmouseout="document.about_button.src='images/about_button.jpg'" href="about.html">
																											<img src="images/about_button.jpg" alt="About" name="about_button"></a></li>
					<li><a onmouseover="document.classes_button.src='images/classes_button_rollover.jpg'" onmouseout="document.classes_button.src='images/classes_button.jpg'" href="classes.html">
																											<img src="images/classes_button.jpg" alt="Classes" name="classes_button"></a></li>
					<li><a onmouseover="document.locations_button.src='images/location_button_rollover.jpg'" onmouseout="document.locations_button.src='images/location_button.jpg'" href="locations.html">
																											<img src="images/location_button.jpg" alt="Locations" name="locations_button"></a></li>
					<li><a onmouseover="document.contact_button.src='images/contact_button_rollover.jpg'" onmouseout="document.contact_button.src='images/contact_button.jpg'" href="contact.html">
																											<img src="images/contact_button.jpg" alt="Contact" name="contact_button"></a></li>
				</ul>
			</div>
			<!-- menu end -->
			
			<?php
				$fname = $_GET["first_name"];
				$lname = $_GET["last_name"];
				$email = $_GET["email"];
				$comment = $_GET["comments"];
				
				$comment = wordwrap($comment, 100);
				$message = $fname .  " " . $lname . ", " . $email . " has sent you the following message:\n" . $comment;
				mail("scott.kelly@ucdconnect.ie", "from Crimson Fitness Website", $message);
				echo "<div id='text-body'><p>Thank you " . $fname . " for your message. We will contact you shortly<br><a href='index.html'>Home.</a></p></div>";
			?>
			
		</div>
		<!-- wrapper end -->
		
		<div id="footer">
			<p>Copyright&copy 2017 <a href="contact.html">Crimson Fitness Ltd.</a>, all rights Reserved. Designed By <a href="mailto:scott.kelly@ucdconnect.ie">Scott Kelly</a>.</p>
		</div>
		<!-- footer end -->
	</body>
</html>
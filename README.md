***

<p>
&nbsp;
<a href="https://github.com/GII/UNIDA">Home</a> 
&nbsp; || &nbsp;
<a href="https://github.com/GII/UNIDA/wiki">Documentation</a> 
&nbsp; || &nbsp;
<a href="https://github.com/GII/UNIDA/wiki/Downloads">Downloads</a>
&nbsp; || &nbsp;
<a href="https://github.com/GII/UNIDA/tree/master/unida_library/src">Sources</a> 
</p>

***

### UniDA Framework


The <a href="http://www.gii.udc.es/unida">UniDA Framework</a> provides an integral solution for building systems that require the integration and interoperation of multiple devices and hardware technologies, like building automation systems, ubiquitous computing systems or ambient intelligence systems.

UniDA is designed to make easier the development of systems that use hardware devices to operate in Human Interaction Environments (HIE). HIEs must be understood as any place where people carry out their daily life, including their work, family life, leisure and social life, interacting with technology to enhance or facilitate the experience. The integration of technology in these environments has been achieved in a disorderly and incompatible way, with devices operating in isolated islands with artificial edges delimited by the manufacturers. UniDA provides developers and installers with a uniform conceptual framework capable of modelling an HIE, together with a set of libraries, tools and devices to build distributed instrumentation networks with support for transparent integration of other technologies.

It is possible to use UniDA for two different, but interrelated, purposes. As an abstraction layer it allows the development of applications that handle hardware devices with independence of the technologies used in each device and their particular characteristics. As a complete HIE instrumentation solution it permits building distributed device networks with support for the transparent integration of existing installations and technologies.

In the first case, the applications have a heterogeneous vision of the network of devices; they must include particular logic to interact with each specific technology and device available in the network, thus complicating the development process and making the addition of new devices more difficult, as they need to take care of all the complexities and particularities of each hardware technology deployed in the installation. In the second case, the applications have a homogeneous vision of the network, they are able to use the same concepts and operations to interact with every device, independently of their underlying technologies. This way they do not require knowledge of specific technologies, they can use devicesfrom different technologies homogeneously, and new devices can be easily added to the network without requiring any modification of the application logic. 

The article [UniDA: Uniform Device Access Framework for Human Interaction Environments](http://www.mdpi.com/1424-8220/11/10/9361) provides a good overview of UniDA and how it can be used to build HIEs and applications that exploit the capabilities of multiple distributed hardware devices.

A detailed documentation of the UniDA Framework is available in this project <a href="https://github.com/GII/UNIDA/wiki">Wiki</a>.

Further information can be found at the project's website: <a href="http://www.gii.udc.es/unida">http://www.gii.udc.es/unida</a>.


*Legal notice: UniDA was developed by the Integrated Group for Engineering Research (GII) of the University of A Coruña for and jointly with Mytech Ingeniería Aplicada S.L. who is its the sole owner. Nevertheless, as its research partner, GII can freely participate in any type of research and development project or publication that implies UniDA and that may improve its capabilities or extend its application to new realms as long as it informs Mytech Ingeniería Aplicada S.L. and preserves its rights under the AGPLv3 license of UniDA.*

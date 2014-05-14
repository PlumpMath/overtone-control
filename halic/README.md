# halic

Halic listens for data (audio signals or Open Sound Control messages) and provides an exploration framework to visualize that data through HCI. As for now the interface is a Clojure Live Coding interface.

## Installation

Clone and Hack AwaY!

## Usage
### SET UP CONNECTION

Make sure you listen for some form of data. I'm using this to visualize Bohrbug's sounds and he enriches them with OSC messages. In the future Halic may or may not incorporatebridges to other data. A central SC server could be used so the more of the synths paramters can be brought into the game at either overtone's :kr or even faster.

### route the data

    (osc/osc-handle server "/test" (fn [msg]
                            (let [x (first (:args msg)) y (rest(:args msg))]
                              (reset! cube1 {:cubesize x})   ;; map it to atoms, so it changes on each message
                              (dofancystuffwith y)  ;; directly use the data in a a function
                              )))

### map it on a canvas (using quil/shadertone)
and watch the visualisation react to it


## Options

none yet

## Examples
first live performance
http://www.trixonline.be/site/content/programma.asp?id=1474



### Bugs

probably none, but I might be wrong



## License

probably some, but I might be wrong


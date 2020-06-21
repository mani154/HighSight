let tooltip = null

function includeToolTip(newTooltip) {
    console.log("includeToolTip called with" + tooltip);
    tooltip = newTooltip;
}

function getTooltip() {
    return tooltip;
}

function closeTooltip() {
    console.log("Tooltip close called");
    if (tooltip) {
        console.log("Tooltip close executing");
        tooltip.hide();
        tooltip.destroy();
        tooltip = null
    }
}





